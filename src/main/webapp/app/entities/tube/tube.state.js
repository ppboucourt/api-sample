(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tube', {
            parent: 'entity',
            url: '/tube',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tubes'
            },
            ncyBreadcrumb: {
                label: 'Tubes',
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tube/tubes.html',
                    controller: 'TubeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('tube-detail', {
            parent: 'entity',
            url: '/tube/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tube'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tube/tube-detail.html',
                    controller: 'TubeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Tube', function($stateParams, Tube) {
                    return Tube.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tube',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tube-detail.edit', {
            parent: 'tube-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tube/tube-dialog.html',
                    controller: 'TubeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tube', function(Tube) {
                            return Tube.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tube.new', {
            parent: 'tube',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tube/tube-dialog.html',
                    controller: 'TubeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tube', null, { reload: 'tube' });
                }, function() {
                    $state.go('tube');
                });
            }]
        })
        .state('tube.edit', {
            parent: 'tube',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tube/tube-dialog.html',
                    controller: 'TubeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tube', function(Tube) {
                            return Tube.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tube', null, { reload: 'tube' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tube.delete', {
            parent: 'tube',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tube/tube-delete-dialog.html',
                    controller: 'TubeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tube', function(Tube) {
                            return Tube.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tube', null, { reload: 'tube' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
