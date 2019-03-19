(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('group-type', {
            parent: 'entity',
            url: '/group-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GroupTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/group-type/group-types.html',
                    controller: 'GroupTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('group-type-detail', {
            parent: 'entity',
            url: '/group-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GroupType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/group-type/group-type-detail.html',
                    controller: 'GroupTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'GroupType', function($stateParams, GroupType) {
                    return GroupType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'group-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('group-type-detail.edit', {
            parent: 'group-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-type/group-type-dialog.html',
                    controller: 'GroupTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GroupType', function(GroupType) {
                            return GroupType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('group-type.new', {
            parent: 'group-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-type/group-type-dialog.html',
                    controller: 'GroupTypeDialogController',
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
                    $state.go('group-type', null, { reload: 'group-type' });
                }, function() {
                    $state.go('group-type');
                });
            }]
        })
        .state('group-type.edit', {
            parent: 'group-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-type/group-type-dialog.html',
                    controller: 'GroupTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GroupType', function(GroupType) {
                            return GroupType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('group-type', null, { reload: 'group-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('group-type.delete', {
            parent: 'group-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-type/group-type-delete-dialog.html',
                    controller: 'GroupTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GroupType', function(GroupType) {
                            return GroupType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('group-type', null, { reload: 'group-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
