(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('fax-send-log', {
            parent: 'entity',
            url: '/fax-send-log?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FaxSendLogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fax-send-log/fax-send-logs.html',
                    controller: 'FaxSendLogController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                serviceProviders: ['ServiceProvider', function(ServiceProvider) {
                    return ServiceProvider.query().$promise;
                }]
            }
        })
        .state('fax-send-log-detail', {
            parent: 'entity',
            url: '/fax-send-log/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FaxSendLog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/fax-send-log/fax-send-log-detail.html',
                    controller: 'FaxSendLogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FaxSendLog', function($stateParams, FaxSendLog) {
                    return FaxSendLog.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'fax-send-log',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        });
        // .state('fax-send-log-detail.edit', {
        //     parent: 'fax-send-log-detail',
        //     url: '/detail/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/fax-send-log/fax-send-log-dialog.html',
        //             controller: 'FaxSendLogDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['FaxSendLog', function(FaxSendLog) {
        //                     return FaxSendLog.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('^', {}, { reload: false });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('fax-send-log.new', {
        //     parent: 'fax-send-log',
        //     url: '/new',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/fax-send-log/fax-send-log-dialog.html',
        //             controller: 'FaxSendLogDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         sid: null,
        //                         sendDate: null,
        //                         faxToNumber: null,
        //                         faxFromNumber: null,
        //                         faxState: null,
        //                         pdfUuid: null,
        //                         mediaUrl: null,
        //                         id: null
        //                     };
        //                 }
        //             }
        //         }).result.then(function() {
        //             $state.go('fax-send-log', null, { reload: 'fax-send-log' });
        //         }, function() {
        //             $state.go('fax-send-log');
        //         });
        //     }]
        // })
        // .state('fax-send-log.edit', {
        //     parent: 'fax-send-log',
        //     url: '/{id}/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/fax-send-log/fax-send-log-dialog.html',
        //             controller: 'FaxSendLogDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['FaxSendLog', function(FaxSendLog) {
        //                     return FaxSendLog.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('fax-send-log', null, { reload: 'fax-send-log' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('fax-send-log.delete', {
        //     parent: 'fax-send-log',
        //     url: '/{id}/delete',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/fax-send-log/fax-send-log-delete-dialog.html',
        //             controller: 'FaxSendLogDeleteController',
        //             controllerAs: 'vm',
        //             size: 'md',
        //             resolve: {
        //                 entity: ['FaxSendLog', function(FaxSendLog) {
        //                     return FaxSendLog.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('fax-send-log', null, { reload: 'fax-send-log' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // });
    }

})();
