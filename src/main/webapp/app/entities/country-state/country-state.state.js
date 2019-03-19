(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('country-state', {
            parent: 'entity',
            url: '/country-state',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CountryStates'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/country-state/country-states.html',
                    controller: 'CountryStateController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('country-state-detail', {
            parent: 'entity',
            url: '/country-state/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'CountryState'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/country-state/country-state-detail.html',
                    controller: 'CountryStateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'CountryState', function($stateParams, CountryState) {
                    return CountryState.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'country-state',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('country-state-detail.edit', {
            parent: 'country-state-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/country-state/country-state-dialog.html',
                    controller: 'CountryStateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CountryState', function(CountryState) {
                            return CountryState.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('country-state.new', {
            parent: 'country-state',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/country-state/country-state-dialog.html',
                    controller: 'CountryStateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                state: null,
                                stateCode: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('country-state', null, { reload: 'country-state' });
                }, function() {
                    $state.go('country-state');
                });
            }]
        })
        .state('country-state.edit', {
            parent: 'country-state',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/country-state/country-state-dialog.html',
                    controller: 'CountryStateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CountryState', function(CountryState) {
                            return CountryState.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('country-state', null, { reload: 'country-state' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('country-state.delete', {
            parent: 'country-state',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/country-state/country-state-delete-dialog.html',
                    controller: 'CountryStateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CountryState', function(CountryState) {
                            return CountryState.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('country-state', null, { reload: 'country-state' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
