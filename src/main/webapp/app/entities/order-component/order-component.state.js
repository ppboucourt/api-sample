(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        // .state('order-component', {
        //     parent: 'entity',
        //     url: '/order-component',
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'OrderComponents'
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/order-component/order-components.html',
        //             controller: 'OrderComponentController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {
        //     }
        // })
        // .state('order-component-detail', {
        //     parent: 'entity',
        //     url: '/order-component/{id}',
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'OrderComponent'
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/order-component/order-component-detail.html',
        //             controller: 'OrderComponentDetailController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {
        //         entity: ['$stateParams', 'OrderComponent', function($stateParams, OrderComponent) {
        //             return OrderComponent.get({id : $stateParams.id}).$promise;
        //         }],
        //         previousState: ["$state", function ($state) {
        //             var currentStateData = {
        //                 name: $state.current.name || 'order-component',
        //                 params: $state.params,
        //                 url: $state.href($state.current.name, $state.params)
        //             };
        //             return currentStateData;
        //         }]
        //     }
        // })
        // .state('order-component-detail.edit', {
        //     parent: 'order-component-detail',
        //     url: '/detail/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/order-component/order-component-dialog.html',
        //             controller: 'OrderComponentDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['OrderComponent', function(OrderComponent) {
        //                     return OrderComponent.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('^', {}, { reload: false });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('order-component.new', {
        //     parent: 'order-component',
        //     url: '/new',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/order-component/order-component-dialog.html',
        //             controller: 'OrderComponentDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         days: null,
        //                         medication: null,
        //                         dosage_form: null,
        //                         dose: null,
        //                         administration_time: null,
        //                         status: null,
        //                         frequency: null,
        //                         id: null
        //                     };
        //                 }
        //             }
        //         }).result.then(function() {
        //             $state.go('order-component', null, { reload: 'order-component' });
        //         }, function() {
        //             $state.go('order-component');
        //         });
        //     }]
        // })
        // .state('order-component.edit', {
        //     parent: 'order-component',
        //     url: '/{id}/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/order-component/order-component-dialog.html',
        //             controller: 'OrderComponentDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['OrderComponent', function(OrderComponent) {
        //                     return OrderComponent.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('order-component', null, { reload: 'order-component' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        .state('order-component-delete', {
            parent: 'all-orders-detail',
            url: '/order-component/{cid}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-component/order-component-delete-dialog.html',
                    controller: 'OrderComponentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OrderComponent', function(OrderComponent) {
                            return OrderComponent.get({id : $stateParams.cid}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('all-orders-detail', {id: $stateParams.id}, { reload: 'all-orders-detail' });
                }, function() {
                    $state.go('all-orders-detail', {id: $stateParams.id}, { reload: 'all-orders-detail' });
                });
            }]
        });
    }

})();
