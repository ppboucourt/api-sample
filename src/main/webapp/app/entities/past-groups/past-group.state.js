(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('past-group-session', {
                parent: 'entity',
                url: '/past-group-session',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Past Group'
                },
                ncyBreadcrumb: {
                    label: 'Past Group'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/past-groups/past-group-session.html',
                        controller: 'PastGroupController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                }
            })

            .state('past-group-session.view', {
                parent: 'past-group-session',
                url: '/{id}/past-group-session-details-view',
                data: {
                    authorities: ['ROLE_USER']
                },
                ncyBreadcrumb: {
                    label: 'Details',
                    parent: 'past-group-session'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/past-groups/past-group-session-details-view.html',
                        controller: 'GroupSessionDetailsViewController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['GroupSessionDetails', '$stateParams', function(GroupSessionDetails, $stateParams) {
                        return GroupSessionDetails.get({id : $stateParams.id}).$promise;
                    }]
                }
            })

            .state('past-group-session.info', {
                parent: 'past-group-session',
                url: '/{id}/past-group-session-details-info',
                data: {
                    authorities: ['ROLE_USER']
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/past-groups/past-group-session-details-view.html',
                        controller: 'GroupSessionDetailsNewController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['GroupSessionDetailsChart', '$stateParams', function(GroupSessionDetailsChart, $stateParams) {
                        return GroupSessionDetailsChart.get({id : $stateParams.id}).$promise;
                    }]
                }
            })

            // .state('occupancy.edit', {
            //     parent: 'occupancy',
            //     url: '/{id}/edit',
            //     data: {
            //         authorities: ['ROLE_USER']
            //     },
            //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
            //         $uibModal.open({
            //             templateUrl: 'app/entities/occupancy/occupancy-edit.html',
            //             controller: 'OccupancyEditController',
            //             controllerAs: 'vm',
            //             animation: true,
            //             backdrop: 'static',
            //             keyboard: false,
            //             size: 'lg',
            //             resolve: {
            //                 entity: ['Bed', function(Bed) {
            //                     return Bed.getWithChart({id : $stateParams.id}).$promise;
            //                 }]
            //             }
            //         }).result.then(function() {
            //             $state.go('occupancy', null, { reload: 'occupancy' });
            //         }, function() {
            //             $state.go('^');
            //         });
            //     }]
            // })
        ;
    }

})();
