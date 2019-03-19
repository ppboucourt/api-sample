(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-contact-facility-type', {
            parent: 'entity',
            url: '/type-contact-facility-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeContactFacilityTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-contact-facility-type/type-contact-facility-types.html',
                    controller: 'TypeContactFacilityTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-contact-facility-type-detail', {
            parent: 'entity',
            url: '/type-contact-facility-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeContactFacilityType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-contact-facility-type/type-contact-facility-type-detail.html',
                    controller: 'TypeContactFacilityTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeContactFacilityType', function($stateParams, TypeContactFacilityType) {
                    return TypeContactFacilityType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-contact-facility-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-contact-facility-type-detail.edit', {
            parent: 'type-contact-facility-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-contact-facility-type/type-contact-facility-type-dialog.html',
                    controller: 'TypeContactFacilityTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeContactFacilityType', function(TypeContactFacilityType) {
                            return TypeContactFacilityType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-contact-facility-type.new', {
            parent: 'type-contact-facility-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-contact-facility-type/type-contact-facility-type-dialog.html',
                    controller: 'TypeContactFacilityTypeDialogController',
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
                    $state.go('type-contact-facility-type', null, { reload: 'type-contact-facility-type' });
                }, function() {
                    $state.go('type-contact-facility-type');
                });
            }]
        })
        .state('type-contact-facility-type.edit', {
            parent: 'type-contact-facility-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-contact-facility-type/type-contact-facility-type-dialog.html',
                    controller: 'TypeContactFacilityTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeContactFacilityType', function(TypeContactFacilityType) {
                            return TypeContactFacilityType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-contact-facility-type', null, { reload: 'type-contact-facility-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-contact-facility-type.delete', {
            parent: 'type-contact-facility-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-contact-facility-type/type-contact-facility-type-delete-dialog.html',
                    controller: 'TypeContactFacilityTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeContactFacilityType', function(TypeContactFacilityType) {
                            return TypeContactFacilityType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-contact-facility-type', null, { reload: 'type-contact-facility-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
