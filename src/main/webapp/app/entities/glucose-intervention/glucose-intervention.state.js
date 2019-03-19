(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('glucose-intervention', {
            parent: 'entity',
            url: '/glucose-intervention',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GlucoseInterventions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/glucose-intervention/glucose-interventions.html',
                    controller: 'GlucoseInterventionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('glucose-intervention-detail', {
            parent: 'entity',
            url: '/glucose-intervention/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'GlucoseIntervention'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/glucose-intervention/glucose-intervention-detail.html',
                    controller: 'GlucoseInterventionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'GlucoseIntervention', function($stateParams, GlucoseIntervention) {
                    return GlucoseIntervention.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'glucose-intervention',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('glucose-intervention-detail.edit', {
            parent: 'glucose-intervention-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/glucose-intervention/glucose-intervention-dialog.html',
                    controller: 'GlucoseInterventionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GlucoseIntervention', function(GlucoseIntervention) {
                            return GlucoseIntervention.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('glucose-intervention.new', {
            parent: 'glucose-intervention',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/glucose-intervention/glucose-intervention-dialog.html',
                    controller: 'GlucoseInterventionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('glucose-intervention', null, { reload: 'glucose-intervention' });
                }, function() {
                    $state.go('glucose-intervention');
                });
            }]
        })
        .state('glucose-intervention.edit', {
            parent: 'glucose-intervention',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/glucose-intervention/glucose-intervention-dialog.html',
                    controller: 'GlucoseInterventionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GlucoseIntervention', function(GlucoseIntervention) {
                            return GlucoseIntervention.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('glucose-intervention', null, { reload: 'glucose-intervention' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('glucose-intervention.delete', {
            parent: 'glucose-intervention',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/glucose-intervention/glucose-intervention-delete-dialog.html',
                    controller: 'GlucoseInterventionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GlucoseIntervention', function(GlucoseIntervention) {
                            return GlucoseIntervention.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('glucose-intervention', null, { reload: 'glucose-intervention' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
