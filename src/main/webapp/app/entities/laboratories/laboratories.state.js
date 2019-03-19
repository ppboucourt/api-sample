(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('laboratories', {
            parent: 'entity',
            url: '/laboratories',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Laboratories'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/laboratories/laboratories.html',
                    controller: 'LaboratoriesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('laboratories-detail', {
            parent: 'entity',
            url: '/laboratories/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Laboratories'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/laboratories/laboratories-detail.html',
                    controller: 'LaboratoriesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Laboratories', function($stateParams, Laboratories) {
                    return Laboratories.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'laboratories',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('laboratories-detail.edit', {
            parent: 'laboratories-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/laboratories/laboratories-dialog.html',
                    controller: 'LaboratoriesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Laboratories', function(Laboratories) {
                            return Laboratories.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('laboratories.new', {
            parent: 'laboratories',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/laboratories/laboratories-dialog.html',
                    controller: 'LaboratoriesDialogController',
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
                    $state.go('laboratories', null, { reload: 'laboratories' });
                }, function() {
                    $state.go('laboratories');
                });
            }]
        })
        .state('laboratories.edit', {
            parent: 'laboratories',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/laboratories/laboratories-dialog.html',
                    controller: 'LaboratoriesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Laboratories', function(Laboratories) {
                            return Laboratories.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('laboratories', null, { reload: 'laboratories' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('laboratories.delete', {
            parent: 'laboratories',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/laboratories/laboratories-delete-dialog.html',
                    controller: 'LaboratoriesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Laboratories', function(Laboratories) {
                            return Laboratories.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('laboratories', null, { reload: 'laboratories' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
