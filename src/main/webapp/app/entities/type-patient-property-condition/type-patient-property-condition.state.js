(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-patient-property-condition', {
            parent: 'entity',
            url: '/type-patient-property-condition',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePatientPropertyConditions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-patient-property-condition/type-patient-property-conditions.html',
                    controller: 'TypePatientPropertyConditionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-patient-property-condition-detail', {
            parent: 'entity',
            url: '/type-patient-property-condition/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePatientPropertyCondition'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-patient-property-condition/type-patient-property-condition-detail.html',
                    controller: 'TypePatientPropertyConditionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypePatientPropertyCondition', function($stateParams, TypePatientPropertyCondition) {
                    return TypePatientPropertyCondition.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-patient-property-condition',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-patient-property-condition-detail.edit', {
            parent: 'type-patient-property-condition-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-property-condition/type-patient-property-condition-dialog.html',
                    controller: 'TypePatientPropertyConditionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePatientPropertyCondition', function(TypePatientPropertyCondition) {
                            return TypePatientPropertyCondition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-patient-property-condition.new', {
            parent: 'type-patient-property-condition',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-property-condition/type-patient-property-condition-dialog.html',
                    controller: 'TypePatientPropertyConditionDialogController',
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
                    $state.go('type-patient-property-condition', null, { reload: 'type-patient-property-condition' });
                }, function() {
                    $state.go('type-patient-property-condition');
                });
            }]
        })
        .state('type-patient-property-condition.edit', {
            parent: 'type-patient-property-condition',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-property-condition/type-patient-property-condition-dialog.html',
                    controller: 'TypePatientPropertyConditionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePatientPropertyCondition', function(TypePatientPropertyCondition) {
                            return TypePatientPropertyCondition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-patient-property-condition', null, { reload: 'type-patient-property-condition' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-patient-property-condition.delete', {
            parent: 'type-patient-property-condition',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-property-condition/type-patient-property-condition-delete-dialog.html',
                    controller: 'TypePatientPropertyConditionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypePatientPropertyCondition', function(TypePatientPropertyCondition) {
                            return TypePatientPropertyCondition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-patient-property-condition', null, { reload: 'type-patient-property-condition' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
