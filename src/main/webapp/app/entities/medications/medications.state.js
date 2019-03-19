(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('medications', {
            parent: 'entity',
            url: '/medications',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Medications'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medications/medications.html',
                    controller: 'MedicationsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('medications-detail', {
            parent: 'entity',
            url: '/medications/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Medications'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medications/medications-detail.html',
                    controller: 'MedicationsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Medications', function($stateParams, Medications) {
                    return Medications.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'medications',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('medications-detail.edit', {
            parent: 'medications-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medications/medications-dialog.html',
                    controller: 'MedicationsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medications', function(Medications) {
                            return Medications.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medications.new', {
            parent: 'medications',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medications/medications-dialog.html',
                    controller: 'MedicationsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                medication: null,
                                route: null,
                                dose: null,
                                dosage_form: null,
                                frequency: null,
                                duration_in_days: null,
                                prn: null,
                                continue_on_discharge: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('medications', null, { reload: 'medications' });
                }, function() {
                    $state.go('medications');
                });
            }]
        })
        .state('medications.edit', {
            parent: 'medications',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medications/medications-dialog.html',
                    controller: 'MedicationsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medications', function(Medications) {
                            return Medications.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medications', null, { reload: 'medications' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medications.delete', {
            parent: 'medications',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medications/medications-delete-dialog.html',
                    controller: 'MedicationsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Medications', function(Medications) {
                            return Medications.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medications', null, { reload: 'medications' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
