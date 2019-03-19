(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-marital-status', {
            parent: 'entity',
            url: '/type-marital-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeMaritalStatuses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-marital-status/type-marital-statuses.html',
                    controller: 'TypeMaritalStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-marital-status-detail', {
            parent: 'entity',
            url: '/type-marital-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeMaritalStatus'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-marital-status/type-marital-status-detail.html',
                    controller: 'TypeMaritalStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeMaritalStatus', function($stateParams, TypeMaritalStatus) {
                    return TypeMaritalStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-marital-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-marital-status-detail.edit', {
            parent: 'type-marital-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-marital-status/type-marital-status-dialog.html',
                    controller: 'TypeMaritalStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeMaritalStatus', function(TypeMaritalStatus) {
                            return TypeMaritalStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-marital-status.new', {
            parent: 'type-marital-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-marital-status/type-marital-status-dialog.html',
                    controller: 'TypeMaritalStatusDialogController',
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
                    $state.go('type-marital-status', null, { reload: 'type-marital-status' });
                }, function() {
                    $state.go('type-marital-status');
                });
            }]
        })
        .state('type-marital-status.edit', {
            parent: 'type-marital-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-marital-status/type-marital-status-dialog.html',
                    controller: 'TypeMaritalStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeMaritalStatus', function(TypeMaritalStatus) {
                            return TypeMaritalStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-marital-status', null, { reload: 'type-marital-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-marital-status.delete', {
            parent: 'type-marital-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-marital-status/type-marital-status-delete-dialog.html',
                    controller: 'TypeMaritalStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeMaritalStatus', function(TypeMaritalStatus) {
                            return TypeMaritalStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-marital-status', null, { reload: 'type-marital-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
