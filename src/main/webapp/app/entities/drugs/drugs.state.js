(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('drugs', {
            parent: 'entity',
            url: '/drugs',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Drugs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/drugs/drugs.html',
                    controller: 'DrugsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('drugs-detail', {
            parent: 'entity',
            url: '/drugs/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Drugs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/drugs/drugs-detail.html',
                    controller: 'DrugsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Drugs', function($stateParams, Drugs) {
                    return Drugs.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'drugs',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('drugs-detail.edit', {
            parent: 'drugs-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drugs/drugs-dialog.html',
                    controller: 'DrugsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Drugs', function(Drugs) {
                            return Drugs.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('drugs.new', {
            parent: 'drugs',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drugs/drugs-dialog.html',
                    controller: 'DrugsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('drugs', null, { reload: 'drugs' });
                }, function() {
                    $state.go('drugs');
                });
            }]
        })
        .state('drugs.edit', {
            parent: 'drugs',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drugs/drugs-dialog.html',
                    controller: 'DrugsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Drugs', function(Drugs) {
                            return Drugs.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('drugs', null, { reload: 'drugs' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('drugs.delete', {
            parent: 'drugs',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drugs/drugs-delete-dialog.html',
                    controller: 'DrugsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Drugs', function(Drugs) {
                            return Drugs.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('drugs', null, { reload: 'drugs' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
