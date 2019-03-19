(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        // .state('diet', {
        //     parent: 'entity',
        //     url: '/diet',
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'Diets'
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/diet/diets.html',
        //             controller: 'DietController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {
        //     }
        // })
        // .state('diet-detail', {
        //     parent: 'entity',
        //     url: '/diet/{id}',
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'Diet'
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/diet/diet-detail.html',
        //             controller: 'DietDetailController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {
        //         entity: ['$stateParams', 'Diet', function($stateParams, Diet) {
        //             return Diet.get({id : $stateParams.id}).$promise;
        //         }],
        //         previousState: ["$state", function ($state) {
        //             var currentStateData = {
        //                 name: $state.current.name || 'diet',
        //                 params: $state.params,
        //                 url: $state.href($state.current.name, $state.params)
        //             };
        //             return currentStateData;
        //         }]
        //     }
        // })
        // .state('diet-detail.edit', {
        //     parent: 'diet-detail',
        //     url: '/detail/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/diet/diet-dialog.html',
        //             controller: 'DietDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['Diet', function(Diet) {
        //                     return Diet.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('^', {}, { reload: false });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('diet.new', {
        //     parent: 'diet',
        //     url: '/new',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/diet/diet-dialog.html',
        //             controller: 'DietDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         description: null,
        //                         status: null,
        //                         id: null
        //                     };
        //                 }
        //             }
        //         }).result.then(function() {
        //             $state.go('diet', null, { reload: 'diet' });
        //         }, function() {
        //             $state.go('diet');
        //         });
        //     }]
        // })
        // .state('diet.edit', {
        //     parent: 'diet',
        //     url: '/{id}/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/diet/diet-dialog.html',
        //             controller: 'DietDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['Diet', function(Diet) {
        //                     return Diet.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('diet', null, { reload: 'diet' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('diet.delete', {
        //     parent: 'diet',
        //     url: '/{id}/delete',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/diet/diet-delete-dialog.html',
        //             controller: 'DietDeleteController',
        //             controllerAs: 'vm',
        //             size: 'md',
        //             resolve: {
        //                 entity: ['Diet', function(Diet) {
        //                     return Diet.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('diet', null, { reload: 'diet' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // });

            .state('diet-detail', {
                parent: 'entity',
                url: '/diet/{aid}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Diet'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/diet/diet-detail.html',
                        cntroller: 'DietDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Diet', function ($stateParams, Diet) {
                        return Diet.get({id: $stateParams.did}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'diet',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            // .state('diet-new', {
            //     parent: 'patient',
            //     url: '/dnew',
            //     data: {
            //         authorities: ['ROLE_USER']
            //     },
            //     onEnter: ['$stateParams', '$state', '$uibModal', 'chart', function ($stateParams, $state, $uibModal, chart) {
            //         $uibModal.open({
            //             templateUrl: 'app/entities/diet/diet-dialog.html',
            //             controller: 'DietDialogController',
            //             controllerAs: 'vm',
            //             backdrop: 'static',
            //             size: 'lg',
            //             resolve: {
            //                 diet: function () {
            //                     return {
            //                         description: null,
            //                         status: 'ACT',
            //                         id: null
            //                     };
            //
            //                 },
            //                 chart: chart
            //             }
            //         }).result.then(function () {
            //             $state.go('patient', null, {reload: 'patient'});
            //         }, function () {
            //             $state.go('patient');
            //         });
            //     }]
            // })
            // .state('diet-edit', {
            //     parent: 'patient',
            //     url: '/{did}/dedit',
            //     data: {
            //         authorities: ['ROLE_USER']
            //     },
            //     onEnter: ['$stateParams', '$state', '$uibModal', 'chart', function ($stateParams, $state, $uibModal, chart) {
            //         $uibModal.open({
            //             templateUrl: 'app/entities/diet/diet-dialog.html',
            //             controller: 'DietDialogController',
            //             controllerAs: 'vm',
            //             backdrop: 'static',
            //             size: 'lg',
            //             resolve: {
            //                 diet: ['Diet', function (Diet) {
            //                     return Diet.get({id: $stateParams.did}).$promise;
            //                 }],
            //                 chart: chart
            //             }
            //         }).result.then(function () {
            //             $state.go('patient', null, {reload: 'patient'});
            //         }, function () {
            //             $state.go('patient');
            //         });
            //     }]
            // })
            .state('diet-delete', {
                parent: 'patient',
                url: '/{did}/ddelete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/diet/diet-delete-dialog.html',
                        controller: 'DietDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Diet', function (Diet) {
                                return Diet.get({id: $stateParams.did}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('patient', null, {reload: 'patient'});
                    }, function () {
                        $state.go('patient');
                    });
                }]
            });
    }
})();
