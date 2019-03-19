(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('patient-order-item', {
            parent: 'entity',
            url: '/clinic-schedule',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PatientOrderItems'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-order-item/patient-order-items2.html',
                    controller: 'PatientOrderItemController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('patient-order-item-detail', {
            parent: 'entity',
            url: '/patient-order-item/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PatientOrderItem'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-order-item/patient-order-item-detail.html',
                    controller: 'PatientOrderItemDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PatientOrderItem', function($stateParams, PatientOrderItem) {
                    return PatientOrderItem.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'patient-order-item',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('patient-order-item-detail.edit', {
            parent: 'patient-order-item-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-order-item/patient-order-item-dialog.html',
                    controller: 'PatientOrderItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PatientOrderItem', function(PatientOrderItem) {
                            return PatientOrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-order-item.new', {
            parent: 'patient-order-item',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-order-item/patient-order-item-dialog.html',
                    controller: 'PatientOrderItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                groupNumberId: null,
                                collected: null,
                                collectedDate: null,
                                scheduleDate: null,
                                sent: null,
                                sentDate: null,
                                readyStatus: null,
                                canceled: null,
                                icd10Codes: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('patient-order-item', null, { reload: 'patient-order-item' });
                }, function() {
                    $state.go('patient-order-item');
                });
            }]
        })
        .state('patient-order-item.edit', {
            parent: 'patient-order-item',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-order-item/patient-order-item-dialog.html',
                    controller: 'PatientOrderItemDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PatientOrderItem', function(PatientOrderItem) {
                            return PatientOrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-order-item', null, { reload: 'patient-order-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-order-item.delete', {
            parent: 'patient-order-item',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-order-item/patient-order-item-delete-dialog.html',
                    controller: 'PatientOrderItemDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PatientOrderItem', function(PatientOrderItem) {
                            return PatientOrderItem.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-order-item', null, { reload: 'patient-order-item' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
