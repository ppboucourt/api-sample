(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        // $stateProvider
            // .state('chart-care-team-add', {
            //     parent: 'patient',
            //     url: '/chart-care-team/{cid}/add',
            //     data: {
            //         authorities: ['ROLE_USER']
            //     },
            //     onEnter: ['$stateParams', '$state', '$uibModal', '$sessionStorage', function ($stateParams, $state, $uibModal, $sessionStorage) {
            //         $uibModal.open({
            //             templateUrl: 'app/entities/chart-care-team/chart-care-team-add-dialog.html',
            //             controller: 'ChartCareTeamAddController',
            //             controllerAs: 'vm',
            //             size: 'md',
            //             resolve: {
            //                 entity: ['ChartCareTeam', function (ChartCareTeam) {
            //
            //                     if ($stateParams.cid) {
            //                         return ChartCareTeam.get({id: $stateParams.cid}).$promise;
            //                     } else {
            //                         return {};
            //                     }
            //                 }]
            //             }
            //         }).result.then(function () {
            //             $state.go('patient', null, {reload: 'patient'});
            //         }, function () {
            //             $state.go('patient');//, null, {reload: 'patient'
            //         });
            //     }]
            // })
            // .state('chart-care-team-delete', {
            //     parent: 'patient',
            //     url: '/chart-care-team/{cid}/delete',
            //     data: {
            //         authorities: ['ROLE_USER']
            //     },
            //     onEnter: ['$stateParams', '$state', '$uibModal', '$sessionStorage', function ($stateParams, $state, $uibModal, $sessionStorage) {
            //         $uibModal.open({
            //             templateUrl: 'app/entities/chart-care-team/chart-care-team-delete-dialog.html',
            //             controller: 'ChartCareTeamDeleteController',
            //             controllerAs: 'vm',
            //             size: 'md',
            //             resolve: {
            //                 entity: ['ChartCareTeam', function (ChartCareTeam) {
            //                     return ChartCareTeam.get({id: $stateParams.cid}).$promise;
            //                 }]
            //             }
            //         }).result.then(function () {
            //             $state.go('patient', null, {reload: 'patient'});
            //         }, function () {
            //             $state.go('patient');//, null, {reload: 'patient'
            //         });
            //     }]
            // });

    }
})();
