(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lab-compendium', {
            parent: 'entity',
            url: '/lab-compendium',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LabCompendiums'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lab-compendium/lab-compendiums.html',
                    controller: 'LabCompendiumController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('lab-compendium-detail', {
            parent: 'entity',
            url: '/lab-compendium/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LabCompendium'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lab-compendium/lab-compendium-detail.html',
                    controller: 'LabCompendiumDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'LabCompendium', function($stateParams, LabCompendium) {
                    return LabCompendium.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lab-compendium',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lab-compendium-detail.edit', {
            parent: 'lab-compendium-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lab-compendium/lab-compendium-dialog.html',
                    controller: 'LabCompendiumDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LabCompendium', function(LabCompendium) {
                            return LabCompendium.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lab-compendium.new', {
            parent: 'lab-compendium',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lab-compendium/lab-compendium-dialog.html',
                    controller: 'LabCompendiumDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                description: null,
                                units: null,
                                specimen: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lab-compendium', null, { reload: 'lab-compendium' });
                }, function() {
                    $state.go('lab-compendium');
                });
            }]
        })
        .state('lab-compendium.edit', {
            parent: 'lab-compendium',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lab-compendium/lab-compendium-dialog.html',
                    controller: 'LabCompendiumDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LabCompendium', function(LabCompendium) {
                            return LabCompendium.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lab-compendium', null, { reload: 'lab-compendium' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lab-compendium.delete', {
            parent: 'lab-compendium',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lab-compendium/lab-compendium-delete-dialog.html',
                    controller: 'LabCompendiumDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LabCompendium', function(LabCompendium) {
                            return LabCompendium.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lab-compendium', null, { reload: 'lab-compendium' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
