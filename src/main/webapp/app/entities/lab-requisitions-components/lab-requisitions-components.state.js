(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lab-requisitions-components', {
            parent: 'entity',
            url: '/lab-requisitions-components',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LabRequisitionsComponents'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lab-requisitions-components/lab-requisitions-components.html',
                    controller: 'LabRequisitionsComponentsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('lab-requisitions-components-detail', {
            parent: 'entity',
            url: '/lab-requisitions-components/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'LabRequisitionsComponents'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lab-requisitions-components/lab-requisitions-components-detail.html',
                    controller: 'LabRequisitionsComponentsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'LabRequisitionsComponents', function($stateParams, LabRequisitionsComponents) {
                    return LabRequisitionsComponents.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lab-requisitions-components',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lab-requisitions-components-detail.edit', {
            parent: 'lab-requisitions-components-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lab-requisitions-components/lab-requisitions-components-dialog.html',
                    controller: 'LabRequisitionsComponentsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LabRequisitionsComponents', function(LabRequisitionsComponents) {
                            return LabRequisitionsComponents.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lab-requisitions-components.new', {
            parent: 'lab-requisitions-components',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lab-requisitions-components/lab-requisitions-components-dialog.html',
                    controller: 'LabRequisitionsComponentsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lab-requisitions-components', null, { reload: 'lab-requisitions-components' });
                }, function() {
                    $state.go('lab-requisitions-components');
                });
            }]
        })
        .state('lab-requisitions-components.edit', {
            parent: 'lab-requisitions-components',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lab-requisitions-components/lab-requisitions-components-dialog.html',
                    controller: 'LabRequisitionsComponentsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['LabRequisitionsComponents', function(LabRequisitionsComponents) {
                            return LabRequisitionsComponents.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lab-requisitions-components', null, { reload: 'lab-requisitions-components' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lab-requisitions-components.delete', {
            parent: 'lab-requisitions-components',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lab-requisitions-components/lab-requisitions-components-delete-dialog.html',
                    controller: 'LabRequisitionsComponentsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['LabRequisitionsComponents', function(LabRequisitionsComponents) {
                            return LabRequisitionsComponents.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lab-requisitions-components', null, { reload: 'lab-requisitions-components' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
