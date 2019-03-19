(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-race', {
            parent: 'entity',
            url: '/type-race',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeRaces'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-race/type-races.html',
                    controller: 'TypeRaceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-race-detail', {
            parent: 'entity',
            url: '/type-race/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeRace'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-race/type-race-detail.html',
                    controller: 'TypeRaceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeRace', function($stateParams, TypeRace) {
                    return TypeRace.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-race',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-race-detail.edit', {
            parent: 'type-race-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-race/type-race-dialog.html',
                    controller: 'TypeRaceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeRace', function(TypeRace) {
                            return TypeRace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-race.new', {
            parent: 'type-race',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-race/type-race-dialog.html',
                    controller: 'TypeRaceDialogController',
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
                    $state.go('type-race', null, { reload: 'type-race' });
                }, function() {
                    $state.go('type-race');
                });
            }]
        })
        .state('type-race.edit', {
            parent: 'type-race',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-race/type-race-dialog.html',
                    controller: 'TypeRaceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeRace', function(TypeRace) {
                            return TypeRace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-race', null, { reload: 'type-race' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-race.delete', {
            parent: 'type-race',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-race/type-race-delete-dialog.html',
                    controller: 'TypeRaceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeRace', function(TypeRace) {
                            return TypeRace.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-race', null, { reload: 'type-race' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
