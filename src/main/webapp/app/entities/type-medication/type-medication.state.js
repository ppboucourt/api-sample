(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-medication', {
            parent: 'entity',
            url: '/type-medication',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeMedications'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-medication/type-medications.html',
                    controller: 'TypeMedicationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-medication-detail', {
            parent: 'entity',
            url: '/type-medication/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeMedication'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-medication/type-medication-detail.html',
                    controller: 'TypeMedicationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeMedication', function($stateParams, TypeMedication) {
                    return TypeMedication.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-medication',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-medication-detail.edit', {
            parent: 'type-medication-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-medication/type-medication-dialog.html',
                    controller: 'TypeMedicationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeMedication', function(TypeMedication) {
                            return TypeMedication.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-medication.new', {
            parent: 'type-medication',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-medication/type-medication-dialog.html',
                    controller: 'TypeMedicationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                chemical_medication: null,
                                trade_name: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('type-medication', null, { reload: 'type-medication' });
                }, function() {
                    $state.go('type-medication');
                });
            }]
        })
        .state('type-medication.edit', {
            parent: 'type-medication',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-medication/type-medication-dialog.html',
                    controller: 'TypeMedicationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeMedication', function(TypeMedication) {
                            return TypeMedication.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-medication', null, { reload: 'type-medication' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-medication.delete', {
            parent: 'type-medication',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-medication/type-medication-delete-dialog.html',
                    controller: 'TypeMedicationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeMedication', function(TypeMedication) {
                            return TypeMedication.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-medication', null, { reload: 'type-medication' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
