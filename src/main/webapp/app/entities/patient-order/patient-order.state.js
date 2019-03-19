(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        // .state('patient-order-current', {
        //     parent: 'patient-abs',
        //     url: '/current-orders',
        //     ncyBreadcrumb: {
        //         label: 'Current Orders'
        //     },
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'Orders'
        //     },
        //     params: {
        //         orderType: 'ONE_TIME'
        //     },
        //     views: {
        //         'content@patient-abs': {
        //             templateUrl: 'app/entities/patient-order/current-order/patient-order-current.html',
        //             controller: 'PatientOrderCurrentController',
        //             controllerAs: 'vm'
        //         },
        //         'onetime@patient-order-current': {
        //             templateUrl: 'app/entities/patient-order/current-order/patient-order-onetime.html',
        //             controller: 'OneTimeCurrentOrderController',
        //             controllerAs: 'vm'
        //         },
        //         'standing@patient-order-current': {
        //             templateUrl: 'app/entities/patient-order/current-order/patient-order-standing.html',
        //             controller: 'StandingCurrentOrderController',
        //             controllerAs: 'vm'
        //         },
        //         'recurring@patient-order-current': {
        //             templateUrl: 'app/entities/patient-order/current-order/patient-order-recurring.html',
        //             controller: 'RecurringCurrentOrderController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {}
        // })
        // .state('patient-order-history', {
        //     parent: 'patient-abs',
        //     url: '/history-orders',
        //     ncyBreadcrumb: {
        //         label: 'History Orders'
        //     },
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'Orders'
        //     },
        //     params: {
        //         orderType: 'ONE_TIME'
        //     },
        //     views: {
        //         'content@patient-abs': {
        //             templateUrl: 'app/entities/patient-order/history-order/patient-order-history.html',
        //             controller: 'PatientOrderHistoryController',
        //             controllerAs: 'vm'
        //         },
        //         'onetime@patient-order-history': {
        //             templateUrl: 'app/entities/patient-order/history-order/patient-order-onetime.html',
        //             controller: 'OneTimeHistoryOrderController',
        //             controllerAs: 'vm'
        //         },
        //         'standing@patient-order-history': {
        //             templateUrl: 'app/entities/patient-order/history-order/patient-order-standing.html',
        //             controller: 'StandingHistoryOrderController',
        //             controllerAs: 'vm'
        //         },
        //         'recurring@patient-order-history': {
        //             templateUrl: 'app/entities/patient-order/history-order/patient-order-recurring.html',
        //             controller: 'RecurringHistoryOrderController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {}
        // })
            .state('patient-orders.new', {
                url: '/new-order',
                ncyBreadcrumb: {
                    label: 'New',
                    parent: 'patient-orders'
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Orders'
                },
                views: {
                    'content@patient-abs': {
                        templateUrl: 'app/entities/patient-order/patient-order-new.html',
                        controller: 'PatientOrderNewController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    patientOrder: ['$stateParams', function ($stateParams) {
                        return {
                            signatureDate: null,
                            signed: null,
                            endDate: null,
                            id: null,
                            patientOrderTests: []
                        };
                    }]
                }
            })
            .state('patient-orders-update', {
                parent: 'patient-orders',
                url: '/{oid}/update',
                ncyBreadcrumb: {
                    label: 'Order details',
                    parent: 'patient-orders'
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Order Details'
                },
                params: {
                    orderType: 'ONE_TIME'
                },
                views: {
                    'content@patient-abs': {
                        templateUrl: 'app/entities/patient-order/patient-order-update.html',
                        controller: 'PatientOrderUpdateController',
                        controllerAs: 'vm'
                    },
                    'order-tests@patient-orders-update': {
                        templateUrl: 'app/entities/patient-order-test/patient-order-tests.html',
                        controller: 'PatientOrderTestController',
                        controllerAs: 'vm',
                        resolve: {
                            canCancel: function () {
                                return true;
                            }
                        }
                    }
                },
                resolve: {
                    patientOrder: ['$stateParams', 'PatientOrder', function ($stateParams, PatientOrder) {
                        return PatientOrder.get({id: $stateParams.oid}).$promise;
                    }],
                }
            }).state('patient-orders-update.cancel', {
            url: '/cancel',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$sessionStorage', '$uibModal', function ($stateParams, $state, $sessionStorage, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-order/patient-order-cancel-dialog.html',
                    controller: 'PatientOrderCancelController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        patientOrder: ['PatientOrder', '$stateParams', '$sessionStorage', function (PatientOrder, $stateParams, $sessionStorage) {
                            return PatientOrder.get({id: $stateParams.oid}).$promise;
                        }]
                    }
                }).result.then(function () {
                    $state.go($sessionStorage.ourl, {"id": $stateParams.id}, {reload: true});
                }, function () {
                    $state.go('^');
                });
            }]
        })
            .state('patient-orders-update.finish', {
                url: '/finish',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/patient-order/patient-order-finish-dialog.html',
                        controller: 'PatientOrderFinishController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            patientOrder: ['PatientOrder', '$stateParams', function (PatientOrder, $stateParams) {
                                return PatientOrder.get({id: $stateParams.oid}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go($sessionStorage.ourl, {"id": $stateParams.id}, {reload: true});
                    }, function () {
                        $state.go('^');
                    });
                }]
            }).state('unsigned-orders', {
                parent: 'entity',
                url: '/unsigned',
                ncyBreadcrumb: {
                    label: 'Unsigned Orders',
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Unsigned Orders'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/patient-order/patient-orders-review.html',
                        controller: 'PatientOrdersReviewController',
                        controllerAs: 'vm'
                    },
                    'orders@unsigned-orders': {
                        templateUrl: 'app/entities/patient-order/patient-order-sign.html',
                        controller: 'SignOrderController',
                        controllerAs: 'vm'
                     },
                    // 'medications@unsigned-orders': {
                    //     templateUrl: 'app/entities/patient-medication/patient-medication-sign.html',
                    //     controller: 'SignMedicationController',
                    //     controllerAs: 'vm'
                    // },
                    // 'actions@unsigned-orders': {
                    //     templateUrl: 'app/entities/patient-action/patient-action-sign.html',
                    //     controller: 'SignActionController',
                    //     controllerAs: 'vm'
                    // }
                },
                resolve: {
                    facility: ['CoreService', function (CoreService) {
                        return CoreService.getCurrentFacility();
                    }]
                }
            })
    }
})();
