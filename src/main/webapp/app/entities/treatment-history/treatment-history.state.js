(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        // .state('treatment-history', {
        //     parent: 'entity',
        //     url: '/treatment-history',
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'TreatmentHistories'
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/treatment-history/treatment-histories.html',
        //             controller: 'TreatmentHistoryController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {
        //     }
        // })
        // .state('treatment-history-detail', {
        //     parent: 'entity',
        //     url: '/treatment-history/{id}',
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'TreatmentHistory'
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/treatment-history/treatment-history-detail.html',
        //             controller: 'TreatmentHistoryDetailController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {
        //         entity: ['$stateParams', 'TreatmentHistory', function($stateParams, TreatmentHistory) {
        //             return TreatmentHistory.get({id : $stateParams.id}).$promise;
        //         }],
        //         previousState: ["$state", function ($state) {
        //             var currentStateData = {
        //                 name: $state.current.name || 'treatment-history',
        //                 params: $state.params,
        //                 url: $state.href($state.current.name, $state.params)
        //             };
        //             return currentStateData;
        //         }]
        //     }
        // })
        // .state('treatment-history-detail.edit', {
        //     parent: 'treatment-history-detail',
        //     url: '/detail/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/treatment-history/treatment-history-dialog.html',
        //             controller: 'TreatmentHistoryDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['TreatmentHistory', function(TreatmentHistory) {
        //                     return TreatmentHistory.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('^', {}, { reload: false });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('treatment-history.new', {
        //     parent: 'treatment-history',
        //     url: '/new',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/treatment-history/treatment-history-dialog.html',
        //             controller: 'TreatmentHistoryDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         name: null,
        //                         date: null,
        //                         id: null
        //                     };
        //                 }
        //             }
        //         }).result.then(function() {
        //             $state.go('treatment-history', null, { reload: 'treatment-history' });
        //         }, function() {
        //             $state.go('treatment-history');
        //         });
        //     }]
        // })
        // .state('treatment-history.edit', {
        //     parent: 'treatment-history',
        //     url: '/{id}/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/treatment-history/treatment-history-dialog.html',
        //             controller: 'TreatmentHistoryDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['TreatmentHistory', function(TreatmentHistory) {
        //                     return TreatmentHistory.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('treatment-history', null, { reload: 'treatment-history' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('treatment-history.delete', {
        //     parent: 'treatment-history',
        //     url: '/{id}/delete',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/treatment-history/treatment-history-delete-dialog.html',
        //             controller: 'TreatmentHistoryDeleteController',
        //             controllerAs: 'vm',
        //             size: 'md',
        //             resolve: {
        //                 entity: ['TreatmentHistory', function(TreatmentHistory) {
        //                     return TreatmentHistory.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('treatment-history', null, { reload: 'treatment-history' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // });

            .state('treatment-history-detail', {
                parent: 'entity',
                url: '/treatment-history/{thid}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'TreatmentHistory'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/treatment-history/treatment-history-detail.html',
                        controller: 'TreatmentHistoryDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'TreatmentHistory', function ($stateParams, TreatmentHistory) {
                        return TreatmentHistory.get({id: $stateParams.thid}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'treatment-history',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('treatment-history-new', {
                parent: 'patient',
                url: '/thnew',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', 'chart', function ($stateParams, $state, $uibModal, chart) {
                    $uibModal.open({
                        templateUrl: 'app/entities/treatment-history/treatment-history-dialog.html',
                        controller: 'TreatmentHistoryDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            treatmentHistory: function () {
                                return {
                                    name: null,
                                    date: null,
                                    id: null,
                                    primaryTherapist: null,
                                    howHear: null,
                                    coordinator: null
                                };
                            },
                            chart: chart
                        }
                    }).result.then(function () {
                        $state.go('patient', null, {reload: 'patient'});
                    }, function () {
                        $state.go('patient');
                    });
                }]
            })
            .state('treatment-history-edit', {
                parent: 'patient',
                url: '/{thid}/thedit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', 'chart', function ($stateParams, $state, $uibModal, chart) {
                    $uibModal.open({
                        templateUrl: 'app/entities/treatment-history/treatment-history-dialog.html',
                        controller: 'TreatmentHistoryDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            treatmentHistory: ['TreatmentHistory', function (TreatmentHistory) {
                                return TreatmentHistory.get({id: $stateParams.thid}).$promise;
                            }],
                            chart: chart
                        }
                    }).result.then(function () {
                        $state.go('patient', null, {reload: 'patient'});
                    }, function () {
                        $state.go('patient');
                    });
                }]
            })
            .state('treatment-history-delete', {
                parent: 'patient',
                url: '/{thid}/thdelete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/treatment-history/treatment-history-delete-dialog.html',
                        controller: 'TreatmentHistoryDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['TreatmentHistory', function (TreatmentHistory) {
                                return TreatmentHistory.get({id: $stateParams.thid}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('patient', null, {reload: 'patient'});
                    }, function () {
                        $state.go('patient');
                    });
                }]
            });
    }

})();
