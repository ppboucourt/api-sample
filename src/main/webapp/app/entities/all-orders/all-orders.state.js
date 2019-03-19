(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
            .state('all-orders', {
                parent: 'entity',
                url: '/all-orders',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                    pageTitle: 'AllOrders'
                },
                ncyBreadcrumb: {
                    label: 'Order'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/all-orders/all-orders.html',
                        controller: 'AllOrdersController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                }
            })
            .state('all-orders-detail', {
                parent: 'entity',
                url: '/all-orders/{id}',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                    pageTitle: 'AllOrders'
                },
                ncyBreadcrumb: {
                    label: 'Order Details',
                    parent: 'all-orders'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/all-orders/all-orders-detail.html',
                        controller: 'AllOrdersDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'AllOrders', function($stateParams, AllOrders) {
                        return AllOrders.get({id : $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'all-orders',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('all-orders-detail.edit', {
                parent: 'all-orders-detail',
                url: '/detail/edit',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/all-orders/all-orders-dialog.html',
                        controller: 'AllOrdersDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['AllOrders', function(AllOrders) {
                                return AllOrders.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('^', {}, { reload: false });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            .state('all-orders.new', {
                parent: 'all-orders',
                url: '/new',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
                },
                ncyBreadcrumb: {
                    label: 'Order Details',
                    parent: 'all-orders'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/all-orders/all-orders-new.html',
                        controller: 'AllOrdersNewController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['CoreService', function (CoreService) {
                        return {
                            name: null,
                            enabled: false,
                            prn: false,
                            indication: null,
                            duration_days: null,
                            note: null,
                            status: 'ACT',
                            facility: CoreService.getCurrentFacility(),
                            id: null,
                            orderComponents: []
                        };
                    }],
                    entityOrderComponent: function () {
                        return {
                            days: null,
                            medication: null,
                            dosage_form: null,
                            dose: null,
                            administration_time: null,
                            status: 'ACT',
                            frequency: null,
                            id: null
                        };
                    }
                }
            })
            .state('all-orders.edit', {
                parent: 'all-orders',
                url: '/{id}/edit',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/all-orders/all-orders-dialog.html',
                        controller: 'AllOrdersDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['AllOrders', function(AllOrders) {
                                return AllOrders.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('all-orders', null, { reload: 'all-orders' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            .state('all-orders.delete', {
                parent: 'all-orders',
                url: '/{id}/delete',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/all-orders/all-orders-delete-dialog.html',
                        controller: 'AllOrdersDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['AllOrders', function(AllOrders) {
                                return AllOrders.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('all-orders', null, { reload: 'all-orders' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })

        ;
    }

})();
