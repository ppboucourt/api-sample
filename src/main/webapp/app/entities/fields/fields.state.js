(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fields', {
            parent: 'entity',
            url: '/fields',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Fields'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fields/fields.html',
                    controller: 'FieldsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('fields-detail', {
            parent: 'entity',
            url: '/fields/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Fields'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fields/fields-detail.html',
                    controller: 'FieldsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Fields', function($stateParams, Fields) {
                    return Fields.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'fields',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('fields-detail.edit', {
            parent: 'fields-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fields/fields-dialog.html',
                    controller: 'FieldsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Fields', function(Fields) {
                            return Fields.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fields.new', {
            parent: 'fields',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fields/fields-dialog.html',
                    controller: 'FieldsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                field_name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('fields', null, { reload: 'fields' });
                }, function() {
                    $state.go('fields');
                });
            }]
        })
        .state('fields.edit', {
            parent: 'fields',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fields/fields-dialog.html',
                    controller: 'FieldsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Fields', function(Fields) {
                            return Fields.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fields', null, { reload: 'fields' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('fields.delete', {
            parent: 'fields',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/fields/fields-delete-dialog.html',
                    controller: 'FieldsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Fields', function(Fields) {
                            return Fields.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('fields', null, { reload: 'fields' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
