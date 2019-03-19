(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('icd-10', {
            parent: 'entity',
            url: '/icd-10',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Icd10S'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/icd-10/icd-10-s.html',
                    controller: 'Icd10Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('icd-10-detail', {
            parent: 'entity',
            url: '/icd-10/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Icd10'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/icd-10/icd-10-detail.html',
                    controller: 'Icd10DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Icd10', function($stateParams, Icd10) {
                    return Icd10.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'icd-10',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('icd-10-detail.edit', {
            parent: 'icd-10-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/icd-10/icd-10-dialog.html',
                    controller: 'Icd10DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Icd10', function(Icd10) {
                            return Icd10.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('icd-10.new', {
            parent: 'icd-10',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/icd-10/icd-10-dialog.html',
                    controller: 'Icd10DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('icd-10', null, { reload: 'icd-10' });
                }, function() {
                    $state.go('icd-10');
                });
            }]
        })
        .state('icd-10.edit', {
            parent: 'icd-10',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/icd-10/icd-10-dialog.html',
                    controller: 'Icd10DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Icd10', function(Icd10) {
                            return Icd10.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('icd-10', null, { reload: 'icd-10' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('icd-10.delete', {
            parent: 'icd-10',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/icd-10/icd-10-delete-dialog.html',
                    controller: 'Icd10DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Icd10', function(Icd10) {
                            return Icd10.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('icd-10', null, { reload: 'icd-10' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
