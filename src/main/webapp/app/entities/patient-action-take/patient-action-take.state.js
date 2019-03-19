(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('patient-action-take', {
            parent: 'entity',
            url: '/patient-action-take',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PatientActionTakes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-action-take/patient-action-takes.html',
                    controller: 'PatientActionTakeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('patient-action-take-detail', {
            parent: 'entity',
            url: '/patient-action-take/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PatientActionTake'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-action-take/patient-action-take-detail.html',
                    controller: 'PatientActionTakeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PatientActionTake', function($stateParams, PatientActionTake) {
                    return PatientActionTake.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'patient-action-take',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('patient-action-take-detail.edit', {
            parent: 'patient-action-take-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-action-take/patient-action-take-dialog.html',
                    controller: 'PatientActionTakeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PatientActionTake', function(PatientActionTake) {
                            return PatientActionTake.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-action-take.new', {
            parent: 'patient-action-take',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-action-take/patient-action-take-dialog.html',
                    controller: 'PatientActionTakeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                collected: null,
                                collectedDate: null,
                                scheduleDate: null,
                                sent: null,
                                sentDate: null,
                                canceled: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('patient-action-take', null, { reload: 'patient-action-take' });
                }, function() {
                    $state.go('patient-action-take');
                });
            }]
        })
        .state('patient-action-take.edit', {
            parent: 'patient-action-take',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-action-take/patient-action-take-dialog.html',
                    controller: 'PatientActionTakeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PatientActionTake', function(PatientActionTake) {
                            return PatientActionTake.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-action-take', null, { reload: 'patient-action-take' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-action-take.delete', {
            parent: 'patient-action-take',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-action-take/patient-action-take-delete-dialog.html',
                    controller: 'PatientActionTakeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PatientActionTake', function(PatientActionTake) {
                            return PatientActionTake.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-action-take', null, { reload: 'patient-action-take' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
