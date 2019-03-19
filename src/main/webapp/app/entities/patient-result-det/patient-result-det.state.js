(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            // .state('latest-result-det', {
            //     parent: 'lab-results',
            //     url: '/{id}/lrdetails',
            //     data: {
            //         authorities: ['ROLE_USER'],
            //         pageTitle: 'Result Details'
            //     },
            //     ncyBreadcrumb: {
            //         label: 'Result Details',
            //         parent: 'lab-results'
            //     },
            //     views: {
            //         'content@': {
            //             templateUrl: 'app/entities/patient-result-det/patient-result-dets.html',
            //             controller: 'PatientResultDetController',
            //             controllerAs: 'vm'
            //         }
            //     },
            //     resolve: {
            //         patientResults: ['$stateParams', 'PatientResult', function ($stateParams, PatientResult) {
            //             return PatientResult.get({id: $stateParams.id}).$promise;
            //         }]
            //     }
            // })
            .state('patient-result-details', {
                parent: 'patient-mars',
                url: '/{rid}/rdetails',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Result Details'
                },
                ncyBreadcrumb: {
                    label: 'Result Details',
                    parent: 'patient-mars'
                },
                views: {
                    'content@patient-abs': {
                        templateUrl: 'app/entities/patient-result-det/patient-result-dets.html',
                        controller: 'PatientResultDetController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    patientResults: ['$stateParams', 'PatientResult', function ($stateParams, PatientResult) {
                        return PatientResult.get({id: $stateParams.rid}).$promise;
                    }]
                }
            })
    }
})();
