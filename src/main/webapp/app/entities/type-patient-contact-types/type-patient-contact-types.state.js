(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-patient-contact-types', {
            parent: 'entity',
            url: '/type-patient-contact-types',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePatientContactTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-patient-contact-types/type-patient-contact-types.html',
                    controller: 'TypePatientContactTypesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-patient-contact-types-detail', {
            parent: 'entity',
            url: '/type-patient-contact-types/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePatientContactTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-patient-contact-types/type-patient-contact-types-detail.html',
                    controller: 'TypePatientContactTypesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypePatientContactTypes', function($stateParams, TypePatientContactTypes) {
                    return TypePatientContactTypes.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-patient-contact-types',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-patient-contact-types-detail.edit', {
            parent: 'type-patient-contact-types-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-contact-types/type-patient-contact-types-dialog.html',
                    controller: 'TypePatientContactTypesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePatientContactTypes', function(TypePatientContactTypes) {
                            return TypePatientContactTypes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-patient-contact-types.new', {
            parent: 'type-patient-contact-types',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-contact-types/type-patient-contact-types-dialog.html',
                    controller: 'TypePatientContactTypesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('type-patient-contact-types', null, { reload: 'type-patient-contact-types' });
                }, function() {
                    $state.go('type-patient-contact-types');
                });
            }]
        })
        .state('type-patient-contact-types.edit', {
            parent: 'type-patient-contact-types',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-contact-types/type-patient-contact-types-dialog.html',
                    controller: 'TypePatientContactTypesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePatientContactTypes', function(TypePatientContactTypes) {
                            return TypePatientContactTypes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-patient-contact-types', null, { reload: 'type-patient-contact-types' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-patient-contact-types.delete', {
            parent: 'type-patient-contact-types',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-contact-types/type-patient-contact-types-delete-dialog.html',
                    controller: 'TypePatientContactTypesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypePatientContactTypes', function(TypePatientContactTypes) {
                            return TypePatientContactTypes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-patient-contact-types', null, { reload: 'type-patient-contact-types' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
