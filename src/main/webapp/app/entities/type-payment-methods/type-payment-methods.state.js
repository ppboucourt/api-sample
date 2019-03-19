(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-payment-methods', {
            parent: 'entity',
            url: '/type-payment-methods',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePaymentMethods'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-payment-methods/type-payment-methods.html',
                    controller: 'TypePaymentMethodsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-payment-methods-detail', {
            parent: 'entity',
            url: '/type-payment-methods/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePaymentMethods'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-payment-methods/type-payment-methods-detail.html',
                    controller: 'TypePaymentMethodsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypePaymentMethods', function($stateParams, TypePaymentMethods) {
                    return TypePaymentMethods.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-payment-methods',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-payment-methods-detail.edit', {
            parent: 'type-payment-methods-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-payment-methods/type-payment-methods-dialog.html',
                    controller: 'TypePaymentMethodsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePaymentMethods', function(TypePaymentMethods) {
                            return TypePaymentMethods.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-payment-methods.new', {
            parent: 'type-payment-methods',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-payment-methods/type-payment-methods-dialog.html',
                    controller: 'TypePaymentMethodsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                status: null,
                                category: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('type-payment-methods', null, { reload: 'type-payment-methods' });
                }, function() {
                    $state.go('type-payment-methods');
                });
            }]
        })
        .state('type-payment-methods.edit', {
            parent: 'type-payment-methods',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-payment-methods/type-payment-methods-dialog.html',
                    controller: 'TypePaymentMethodsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePaymentMethods', function(TypePaymentMethods) {
                            return TypePaymentMethods.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-payment-methods', null, { reload: 'type-payment-methods' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-payment-methods.delete', {
            parent: 'type-payment-methods',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-payment-methods/type-payment-methods-delete-dialog.html',
                    controller: 'TypePaymentMethodsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypePaymentMethods', function(TypePaymentMethods) {
                            return TypePaymentMethods.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-payment-methods', null, { reload: 'type-payment-methods' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
