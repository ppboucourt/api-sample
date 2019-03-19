(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('insurance-carrier', {
            parent: 'entity',
            url: '/insurance-carrier',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InsuranceCarriers'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/insurance-carrier/insurance-carriers.html',
                    controller: 'InsuranceCarrierController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('insurance-carrier-detail', {
            parent: 'entity',
            url: '/insurance-carrier/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InsuranceCarrier'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/insurance-carrier/insurance-carrier-detail.html',
                    controller: 'InsuranceCarrierDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'InsuranceCarrier', function($stateParams, InsuranceCarrier) {
                    return InsuranceCarrier.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'insurance-carrier',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('insurance-carrier-detail.edit', {
            parent: 'insurance-carrier-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-carrier/insurance-carrier-dialog.html',
                    controller: 'InsuranceCarrierDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InsuranceCarrier', function(InsuranceCarrier) {
                            return InsuranceCarrier.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('insurance-carrier.new', {
            parent: 'insurance-carrier',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-carrier/insurance-carrier-dialog.html',
                    controller: 'InsuranceCarrierDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                acctno: null,
                                name: null,
                                address: null,
                                address2: null,
                                city: null,
                                state: null,
                                zip: null,
                                phone: null,
                                iType: null,
                                etin: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('insurance-carrier', null, { reload: 'insurance-carrier' });
                }, function() {
                    $state.go('insurance-carrier');
                });
            }]
        })
        .state('insurance-carrier.edit', {
            parent: 'insurance-carrier',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-carrier/insurance-carrier-dialog.html',
                    controller: 'InsuranceCarrierDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InsuranceCarrier', function(InsuranceCarrier) {
                            return InsuranceCarrier.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('insurance-carrier', null, { reload: 'insurance-carrier' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('insurance-carrier.delete', {
            parent: 'insurance-carrier',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-carrier/insurance-carrier-delete-dialog.html',
                    controller: 'InsuranceCarrierDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['InsuranceCarrier', function(InsuranceCarrier) {
                            return InsuranceCarrier.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('insurance-carrier', null, { reload: 'insurance-carrier' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
