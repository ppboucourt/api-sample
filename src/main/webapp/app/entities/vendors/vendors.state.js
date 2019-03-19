(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('vendors', {
            parent: 'entity',
            url: '/vendors',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Vendors'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vendors/vendors.html',
                    controller: 'VendorsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('vendors-detail', {
            parent: 'entity',
            url: '/vendors/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Vendors'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vendors/vendors-detail.html',
                    controller: 'VendorsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Vendors', function($stateParams, Vendors) {
                    return Vendors.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'vendors',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('vendors-detail.edit', {
            parent: 'vendors-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vendors/vendors-dialog.html',
                    controller: 'VendorsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Vendors', function(Vendors) {
                            return Vendors.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vendors.new', {
            parent: 'vendors',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vendors/vendors-dialog.html',
                    controller: 'VendorsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                company_name: null,
                                use_this_vendor: null,
                                contact_name: null,
                                contact_phone: null,
                                address: null,
                                address_two: null,
                                city: null,
                                state: null,
                                zip: null,
                                company_phone: null,
                                company_fax: null,
                                notes: null,
                                fax_order_number: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('vendors', null, { reload: 'vendors' });
                }, function() {
                    $state.go('vendors');
                });
            }]
        })
        .state('vendors.edit', {
            parent: 'vendors',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vendors/vendors-dialog.html',
                    controller: 'VendorsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Vendors', function(Vendors) {
                            return Vendors.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vendors', null, { reload: 'vendors' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vendors.delete', {
            parent: 'vendors',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vendors/vendors-delete-dialog.html',
                    controller: 'VendorsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Vendors', function(Vendors) {
                            return Vendors.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vendors', null, { reload: 'vendors' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
