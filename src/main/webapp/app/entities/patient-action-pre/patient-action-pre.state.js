(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('patient-action-pre', {
            parent: 'entity',
            url: '/patient-action-pre',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PatientActionPres'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-action-pre/patient-action-pres.html',
                    controller: 'PatientActionPreController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('patient-action-pre-detail', {
            parent: 'entity',
            url: '/patient-action-pre/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PatientActionPre'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-action-pre/patient-action-pre-detail.html',
                    controller: 'PatientActionPreDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PatientActionPre', function($stateParams, PatientActionPre) {
                    return PatientActionPre.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'patient-action-pre',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('patient-action-pre-detail.edit', {
            parent: 'patient-action-pre-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-action-pre/patient-action-pre-dialog.html',
                    controller: 'PatientActionPreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PatientActionPre', function(PatientActionPre) {
                            return PatientActionPre.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-action-pre.new', {
            parent: 'patient-action-pre',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-action-pre/patient-action-pre-dialog.html',
                    controller: 'PatientActionPreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                staringDate: null,
                                endDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('patient-action-pre', null, { reload: 'patient-action-pre' });
                }, function() {
                    $state.go('patient-action-pre');
                });
            }]
        })
        .state('patient-action-pre.edit', {
            parent: 'patient-action-pre',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-action-pre/patient-action-pre-dialog.html',
                    controller: 'PatientActionPreDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PatientActionPre', function(PatientActionPre) {
                            return PatientActionPre.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-action-pre', null, { reload: 'patient-action-pre' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-action-pre.delete', {
            parent: 'patient-action-pre',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-action-pre/patient-action-pre-delete-dialog.html',
                    controller: 'PatientActionPreDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PatientActionPre', function(PatientActionPre) {
                            return PatientActionPre.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-action-pre', null, { reload: 'patient-action-pre' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
