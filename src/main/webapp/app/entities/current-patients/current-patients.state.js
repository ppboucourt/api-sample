(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        .state('current-patients', {
            parent: 'entity',
            url: '/patients',
            data: {
                authorities: [
                    ROLES.ROLE_USER,
                    ROLES.ROLE_SUPER_ADMIN,
                    ROLES.ROLE_PROGRAM_DIRECTOR,
                    ROLES.ROLE_CASE_MANAGER,
                    ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE,
                    ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                    ROLES.ROLE_PRIMARY_THERAPIST,
                    ROLES.ROLE_OTHER_THERAPIST,
                    ROLES.ROLE_BHT
                ],
                pageTitle: 'Current Patients'
            },
            ncyBreadcrumb: {
                label: 'Current Patient'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/current-patients/current-patients.html',
                    controller: 'CurrentPatientController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                chart : function() {
                    return {
                        first_name: null,
                        last_name: null,
                        mr_no: null,
                        dischargeDateFrom: null,
                        dischargeDateTo: null,
                        admissionDateFrom: null,
                        admissionDateTo: null,
                        typePatientProgram: {}
                    }
                }
            }
        })
        // .state('chart-detail', {
        //     parent: 'entity',
        //     url: '/chart/{id}',
        //     data: {
        //         authorities: ['ROLE_USER'],
        //         pageTitle: 'Chart'
        //     },
        //     views: {
        //         'content@': {
        //             templateUrl: 'app/entities/chart/chart-detail.html',
        //             controller: 'ChartDetailController',
        //             controllerAs: 'vm'
        //         }
        //     },
        //     resolve: {
        //         entity: ['$stateParams', 'Chart', function($stateParams, Chart) {
        //             return Chart.get({id : $stateParams.id}).$promise;
        //         }],
        //         previousState: ["$state", function ($state) {
        //             var currentStateData = {
        //                 name: $state.current.name || 'chart',
        //                 params: $state.params,
        //                 url: $state.href($state.current.name, $state.params)
        //             };
        //             return currentStateData;
        //         }]
        //     }
        // })
        // .state('chart-detail.edit', {
        //     parent: 'chart-detail',
        //     url: '/detail/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/chart/chart-dialog.html',
        //             controller: 'ChartDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['Chart', function(Chart) {
        //                     return Chart.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('^', {}, { reload: false });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('chart.new', {
        //     parent: 'chart',
        //     url: '/new',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/chart/chart-dialog.html',
        //             controller: 'ChartDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: function () {
        //                     return {
        //                         ur_loc: null,
        //                         admission_date: null,
        //                         discharge_time: null,
        //                         discharge_to: null,
        //                         discharge_type: null,
        //                         program: null,
        //                         rep_name: null,
        //                         referrer: null,
        //                         marital_status: null,
        //                         phone: null,
        //                         race: null,
        //                         ethnicity: null,
        //                         address: null,
        //                         address_two: null,
        //                         city: null,
        //                         state: null,
        //                         zip: null,
        //                         payment_method: null,
        //                         date_first_contact: null,
        //                         first_contact_name: null,
        //                         first_contact_relationship: null,
        //                         occupancy: null,
        //                         employer: null,
        //                         employer_phone: null,
        //                         status: null,
        //                         mr_no: null,
        //                         first_contact_phone: null,
        //                         alt_phone: null,
        //                         email: null,
        //                         sobriety_date: null,
        //                         picture: null,
        //                         pictureContentType: null,
        //                         patient_license: null,
        //                         patient_licenseContentType: null,
        //                         one_time_only: null,
        //                         referrer_required_contact: null,
        //                         waitingRoom: null,
        //                         dischargeDate: null,
        //                         id: null
        //                     };
        //                 }
        //             }
        //         }).result.then(function() {
        //             $state.go('chart', null, { reload: 'chart' });
        //         }, function() {
        //             $state.go('chart');
        //         });
        //     }]
        // })
        // .state('chart.edit', {
        //     parent: 'chart',
        //     url: '/{id}/edit',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/chart/chart-dialog.html',
        //             controller: 'ChartDialogController',
        //             controllerAs: 'vm',
        //             backdrop: 'static',
        //             size: 'lg',
        //             resolve: {
        //                 entity: ['Chart', function(Chart) {
        //                     return Chart.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('chart', null, { reload: 'chart' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        // .state('chart.delete', {
        //     parent: 'chart',
        //     url: '/{id}/delete',
        //     data: {
        //         authorities: ['ROLE_USER']
        //     },
        //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
        //         $uibModal.open({
        //             templateUrl: 'app/entities/chart/chart-delete-dialog.html',
        //             controller: 'ChartDeleteController',
        //             controllerAs: 'vm',
        //             size: 'md',
        //             resolve: {
        //                 entity: ['Chart', function(Chart) {
        //                     return Chart.get({id : $stateParams.id}).$promise;
        //                 }]
        //             }
        //         }).result.then(function() {
        //             $state.go('chart', null, { reload: 'chart' });
        //         }, function() {
        //             $state.go('^');
        //         });
        //     }]
        // })
        ;
    }

})();
