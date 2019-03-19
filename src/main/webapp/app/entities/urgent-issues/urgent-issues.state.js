(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('urgent-issues', {
            parent: 'entity',
            url: '/urgent-issues',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'UrgentIssues'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/urgent-issues/urgent-issues.html',
                    controller: 'UrgentIssuesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                chart: ['$stateParams', 'Chart', 'CoreService', function($stateParams, Chart, CoreService) {
                    return Chart.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        // .state('urgent-issues-detail', {
        //     parent: 'entity',
        //     url: '/urgent-issues/{id}',
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'UrgentIssues'
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/urgent-issues/urgent-issues-detail.html',
        //             controller: 'UrgentIssuesDetailController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {
        //         entity: ['$stateParams', 'UrgentIssues', function($stateParams, UrgentIssues) {
        //             return UrgentIssues.get({id : $stateParams.id}).$promise;
        //         }],
        //         previousState: ["$state", function ($state) {
        //             var currentStateData = {
        //                 name: $state.current.name || 'urgent-issues',
        //                 params: $state.params,
        //                 url: $state.href($state.current.name, $state.params)
        //             };
        //             return currentStateData;
        //         }]
        //     }
        // })
        // .state('urgent-issues-detail.edit', {
        //     parent: 'urgent-issues-detail',
        //     url: '/detail/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/urgent-issues/urgent-issues-dialog.html',
        //             controller: 'UrgentIssuesDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['UrgentIssues', function(UrgentIssues) {
        //                     return UrgentIssues.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('^', {}, { reload: false });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        .state('urgent-issues-new', {
            parent: 'patient',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/urgent-issues/urgent-issues-dialog.html',
                    controller: 'UrgentIssuesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        urgentIssue: function () {
                            return {
                                description: null,
                                status: null,
                                id: null
                            };
                        },
                        chart: ['$stateParams', 'Chart', 'CoreService', function($stateParams, Chart, CoreService) {
                            return Chart.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient', null, { reload: 'patient' });
                }, function() {
                    $state.go('patient');
                });
            }]
        })
        .state('urgent-issues-edit', {
            parent: 'patient',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/urgent-issues/urgent-issues-dialog.html',
                    controller: 'UrgentIssuesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UrgentIssues', function(UrgentIssues) {
                            return UrgentIssues.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('urgent-issues', null, { reload: 'urgent-issues' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('urgent-issues-delete', {
            parent: 'patient',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/urgent-issues/urgent-issues-delete-dialog.html',
                    controller: 'UrgentIssuesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UrgentIssues', function(UrgentIssues) {
                            return UrgentIssues.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('urgent-issues', null, { reload: 'urgent-issues' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
