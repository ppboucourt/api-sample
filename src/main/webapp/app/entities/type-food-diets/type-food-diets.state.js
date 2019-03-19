(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-food-diets', {
            parent: 'entity',
            url: '/type-food-diets',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeFoodDiets'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-food-diets/type-food-diets.html',
                    controller: 'TypeFoodDietsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-food-diets-detail', {
            parent: 'entity',
            url: '/type-food-diets/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeFoodDiets'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-food-diets/type-food-diets-detail.html',
                    controller: 'TypeFoodDietsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeFoodDiets', function($stateParams, TypeFoodDiets) {
                    return TypeFoodDiets.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-food-diets',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-food-diets-detail.edit', {
            parent: 'type-food-diets-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-food-diets/type-food-diets-dialog.html',
                    controller: 'TypeFoodDietsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeFoodDiets', function(TypeFoodDiets) {
                            return TypeFoodDiets.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-food-diets.new', {
            parent: 'type-food-diets',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-food-diets/type-food-diets-dialog.html',
                    controller: 'TypeFoodDietsDialogController',
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
                    $state.go('type-food-diets', null, { reload: 'type-food-diets' });
                }, function() {
                    $state.go('type-food-diets');
                });
            }]
        })
        .state('type-food-diets.edit', {
            parent: 'type-food-diets',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-food-diets/type-food-diets-dialog.html',
                    controller: 'TypeFoodDietsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeFoodDiets', function(TypeFoodDiets) {
                            return TypeFoodDiets.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-food-diets', null, { reload: 'type-food-diets' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-food-diets.delete', {
            parent: 'type-food-diets',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-food-diets/type-food-diets-delete-dialog.html',
                    controller: 'TypeFoodDietsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeFoodDiets', function(TypeFoodDiets) {
                            return TypeFoodDiets.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-food-diets', null, { reload: 'type-food-diets' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
