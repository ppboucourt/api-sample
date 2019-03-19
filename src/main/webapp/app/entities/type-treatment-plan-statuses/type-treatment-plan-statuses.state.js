(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-treatment-plan-statuses', {
            parent: 'entity',
            url: '/type-treatment-plan-statuses',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeTreatmentPlanStatuses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-treatment-plan-statuses/type-treatment-plan-statuses.html',
                    controller: 'TypeTreatmentPlanStatusesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-treatment-plan-statuses-detail', {
            parent: 'entity',
            url: '/type-treatment-plan-statuses/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeTreatmentPlanStatuses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-treatment-plan-statuses/type-treatment-plan-statuses-detail.html',
                    controller: 'TypeTreatmentPlanStatusesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeTreatmentPlanStatuses', function($stateParams, TypeTreatmentPlanStatuses) {
                    return TypeTreatmentPlanStatuses.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-treatment-plan-statuses',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-treatment-plan-statuses-detail.edit', {
            parent: 'type-treatment-plan-statuses-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-treatment-plan-statuses/type-treatment-plan-statuses-dialog.html',
                    controller: 'TypeTreatmentPlanStatusesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeTreatmentPlanStatuses', function(TypeTreatmentPlanStatuses) {
                            return TypeTreatmentPlanStatuses.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-treatment-plan-statuses.new', {
            parent: 'type-treatment-plan-statuses',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-treatment-plan-statuses/type-treatment-plan-statuses-dialog.html',
                    controller: 'TypeTreatmentPlanStatusesDialogController',
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
                    $state.go('type-treatment-plan-statuses', null, { reload: 'type-treatment-plan-statuses' });
                }, function() {
                    $state.go('type-treatment-plan-statuses');
                });
            }]
        })
        .state('type-treatment-plan-statuses.edit', {
            parent: 'type-treatment-plan-statuses',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-treatment-plan-statuses/type-treatment-plan-statuses-dialog.html',
                    controller: 'TypeTreatmentPlanStatusesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeTreatmentPlanStatuses', function(TypeTreatmentPlanStatuses) {
                            return TypeTreatmentPlanStatuses.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-treatment-plan-statuses', null, { reload: 'type-treatment-plan-statuses' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-treatment-plan-statuses.delete', {
            parent: 'type-treatment-plan-statuses',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-treatment-plan-statuses/type-treatment-plan-statuses-delete-dialog.html',
                    controller: 'TypeTreatmentPlanStatusesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeTreatmentPlanStatuses', function(TypeTreatmentPlanStatuses) {
                            return TypeTreatmentPlanStatuses.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-treatment-plan-statuses', null, { reload: 'type-treatment-plan-statuses' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
