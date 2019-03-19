(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-patient-contacts-relationship', {
            parent: 'entity',
            url: '/type-patient-contacts-relationship',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePatientContactsRelationships'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-patient-contacts-relationship/type-patient-contacts-relationships.html',
                    controller: 'TypePatientContactsRelationshipController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-patient-contacts-relationship-detail', {
            parent: 'entity',
            url: '/type-patient-contacts-relationship/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePatientContactsRelationship'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-patient-contacts-relationship/type-patient-contacts-relationship-detail.html',
                    controller: 'TypePatientContactsRelationshipDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypePatientContactsRelationship', function($stateParams, TypePatientContactsRelationship) {
                    return TypePatientContactsRelationship.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-patient-contacts-relationship',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-patient-contacts-relationship-detail.edit', {
            parent: 'type-patient-contacts-relationship-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-contacts-relationship/type-patient-contacts-relationship-dialog.html',
                    controller: 'TypePatientContactsRelationshipDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePatientContactsRelationship', function(TypePatientContactsRelationship) {
                            return TypePatientContactsRelationship.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-patient-contacts-relationship.new', {
            parent: 'type-patient-contacts-relationship',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-contacts-relationship/type-patient-contacts-relationship-dialog.html',
                    controller: 'TypePatientContactsRelationshipDialogController',
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
                    $state.go('type-patient-contacts-relationship', null, { reload: 'type-patient-contacts-relationship' });
                }, function() {
                    $state.go('type-patient-contacts-relationship');
                });
            }]
        })
        .state('type-patient-contacts-relationship.edit', {
            parent: 'type-patient-contacts-relationship',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-contacts-relationship/type-patient-contacts-relationship-dialog.html',
                    controller: 'TypePatientContactsRelationshipDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePatientContactsRelationship', function(TypePatientContactsRelationship) {
                            return TypePatientContactsRelationship.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-patient-contacts-relationship', null, { reload: 'type-patient-contacts-relationship' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-patient-contacts-relationship.delete', {
            parent: 'type-patient-contacts-relationship',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-contacts-relationship/type-patient-contacts-relationship-delete-dialog.html',
                    controller: 'TypePatientContactsRelationshipDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypePatientContactsRelationship', function(TypePatientContactsRelationship) {
                            return TypePatientContactsRelationship.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-patient-contacts-relationship', null, { reload: 'type-patient-contacts-relationship' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
