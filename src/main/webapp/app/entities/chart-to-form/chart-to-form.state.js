(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('chart-to-form', {
            parent: 'entity',
            url: '/chart-to-form',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChartToForms'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chart-to-form/chart-to-forms.html',
                    controller: 'ChartToFormController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('chart-to-form-detail', {
            parent: 'entity',
            url: '/chart-to-form/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChartToForm'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chart-to-form/chart-to-form-detail.html',
                    controller: 'ChartToFormDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ChartToForm', function($stateParams, ChartToForm) {
                    return ChartToForm.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'chart-to-form',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('chart-to-form-detail.edit', {
            parent: 'chart-to-form-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-form/chart-to-form-dialog.html',
                    controller: 'ChartToFormDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChartToForm', function(ChartToForm) {
                            return ChartToForm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chart-to-form.new', {
            parent: 'chart-to-form',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-form/chart-to-form-dialog.html',
                    controller: 'ChartToFormDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                signature: null,
                                signed: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('chart-to-form', null, { reload: 'chart-to-form' });
                }, function() {
                    $state.go('chart-to-form');
                });
            }]
        })
        .state('chart-to-form.edit', {
            parent: 'chart-to-form',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-form/chart-to-form-dialog.html',
                    controller: 'ChartToFormDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChartToForm', function(ChartToForm) {
                            return ChartToForm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chart-to-form', null, { reload: 'chart-to-form' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chart-to-form.delete', {
            parent: 'chart-to-form',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-form/chart-to-form-delete-dialog.html',
                    controller: 'ChartToFormDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ChartToForm', function(ChartToForm) {
                            return ChartToForm.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chart-to-form', null, { reload: 'chart-to-form' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
