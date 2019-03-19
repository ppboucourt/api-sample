(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-dosage', {
            parent: 'entity',
            url: '/type-dosage',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeDosages'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-dosage/type-dosages.html',
                    controller: 'TypeDosageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-dosage-detail', {
            parent: 'entity',
            url: '/type-dosage/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeDosage'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-dosage/type-dosage-detail.html',
                    controller: 'TypeDosageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeDosage', function($stateParams, TypeDosage) {
                    return TypeDosage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-dosage',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-dosage-detail.edit', {
            parent: 'type-dosage-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-dosage/type-dosage-dialog.html',
                    controller: 'TypeDosageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeDosage', function(TypeDosage) {
                            return TypeDosage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-dosage.new', {
            parent: 'type-dosage',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-dosage/type-dosage-dialog.html',
                    controller: 'TypeDosageDialogController',
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
                    $state.go('type-dosage', null, { reload: 'type-dosage' });
                }, function() {
                    $state.go('type-dosage');
                });
            }]
        })
        .state('type-dosage.edit', {
            parent: 'type-dosage',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-dosage/type-dosage-dialog.html',
                    controller: 'TypeDosageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeDosage', function(TypeDosage) {
                            return TypeDosage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-dosage', null, { reload: 'type-dosage' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-dosage.delete', {
            parent: 'type-dosage',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-dosage/type-dosage-delete-dialog.html',
                    controller: 'TypeDosageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeDosage', function(TypeDosage) {
                            return TypeDosage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-dosage', null, { reload: 'type-dosage' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
