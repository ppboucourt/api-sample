(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('case-load', {
            parent: 'entity',
            url: '/case-load',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Case Load'
            },
            ncyBreadcrumb: {
                label: 'Case Load'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/case-load/case-load.html',
                    controller: 'CaseLoadController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        // .state('care-manager-detail', {
        //     parent: 'entity',
        //     url: '/care-manager/{id}',
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'CareManager'
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/care-manager/care-manager-detail.html',
        //             controller: 'CareManagerDetailController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {
        //         entity: ['$stateParams', 'CareManager', function($stateParams, CareManager) {
        //             return CareManager.get({id : $stateParams.id}).$promise;
        //         }],
        //         previousState: ["$state", function ($state) {
        //             var currentStateData = {
        //                 name: $state.current.name || 'care-manager',
        //                 params: $state.params,
        //                 url: $state.href($state.current.name, $state.params)
        //             };
        //             return currentStateData;
        //         }]
        //     }
        // })
        // .state('care-manager-detail.edit', {
        //     parent: 'care-manager-detail',
        //     url: '/detail/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/care-manager/care-manager-dialog.html',
        //             controller: 'CareManagerDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['CareManager', function(CareManager) {
        //                     return CareManager.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('^', {}, { reload: false });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('care-manager.new', {
        //     parent: 'care-manager',
        //     url: '/new',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/care-manager/care-manager-dialog.html',
        //             controller: 'CareManagerDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         full_name: null,
        //                         phone: null,
        //                         insurance_company: null,
        //                         notes: null,
        //                         status: null,
        //                         id: null
        //                     };
        //                 }
        //             }
        //         }).result.then(function() {
        //             $state.go('care-manager', null, { reload: 'care-manager' });
        //         }, function() {
        //             $state.go('care-manager');
        //         });
        //     }]
        // })
        // .state('care-manager.edit', {
        //     parent: 'care-manager',
        //     url: '/{id}/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/care-manager/care-manager-dialog.html',
        //             controller: 'CareManagerDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['CareManager', function(CareManager) {
        //                     return CareManager.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('care-manager', null, { reload: 'care-manager' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('care-manager.delete', {
        //     parent: 'care-manager',
        //     url: '/{id}/delete',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/care-manager/care-manager-delete-dialog.html',
        //             controller: 'CareManagerDeleteController',
        //             controllerAs: 'vm',
        //             size: 'md',
        //             resolve: {
        //                 entity: ['CareManager', function(CareManager) {
        //                     return CareManager.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('care-manager', null, { reload: 'care-manager' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        ;
    }

})();
