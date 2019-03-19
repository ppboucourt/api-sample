(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('report-details', {
            parent: 'entity',
            url: '/report-details',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ReportDetails'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/report-details/report-details.html',
                    controller: 'ReportDetailsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('report-details-detail', {
            parent: 'entity',
            url: '/report-details/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ReportDetails'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/report-details/report-details-detail.html',
                    controller: 'ReportDetailsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ReportDetails', function($stateParams, ReportDetails) {
                    return ReportDetails.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'report-details',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('report-details-detail.edit', {
            parent: 'report-details-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/report-details/report-details-dialog.html',
                    controller: 'ReportDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReportDetails', function(ReportDetails) {
                            return ReportDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('report-details.new', {
            parent: 'report-details',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/report-details/report-details-dialog.html',
                    controller: 'ReportDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                sorting: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('report-details', null, { reload: 'report-details' });
                }, function() {
                    $state.go('report-details');
                });
            }]
        })
        .state('report-details.edit', {
            parent: 'report-details',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/report-details/report-details-dialog.html',
                    controller: 'ReportDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReportDetails', function(ReportDetails) {
                            return ReportDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('report-details', null, { reload: 'report-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('report-details.delete', {
            parent: 'report-details',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/report-details/report-details-delete-dialog.html',
                    controller: 'ReportDetailsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ReportDetails', function(ReportDetails) {
                            return ReportDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('report-details', null, { reload: 'report-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
