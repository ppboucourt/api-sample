(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('payer', {
            parent: 'entity',
            url: '/payer',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Payers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/payer/payers.html',
                    controller: 'PayerController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('payer-detail', {
            parent: 'entity',
            url: '/payer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Payer'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/payer/payer-detail.html',
                    controller: 'PayerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Payer', function($stateParams, Payer) {
                    return Payer.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'payer',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('payer-detail.edit', {
            parent: 'payer-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payer/payer-dialog.html',
                    controller: 'PayerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Payer', function(Payer) {
                            return Payer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('payer.new', {
            parent: 'payer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payer/payer-dialog.html',
                    controller: 'PayerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                insurance_company: null,
                                address: null,
                                address_two: null,
                                city: null,
                                state: null,
                                zip: null,
                                phone: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('payer', null, { reload: 'payer' });
                }, function() {
                    $state.go('payer');
                });
            }]
        })
        .state('payer.edit', {
            parent: 'payer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payer/payer-dialog.html',
                    controller: 'PayerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Payer', function(Payer) {
                            return Payer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('payer', null, { reload: 'payer' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('payer.delete', {
            parent: 'payer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/payer/payer-delete-dialog.html',
                    controller: 'PayerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Payer', function(Payer) {
                            return Payer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('payer', null, { reload: 'payer' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
