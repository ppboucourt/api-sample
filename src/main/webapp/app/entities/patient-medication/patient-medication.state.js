(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            // .state('patient-medication', {
            //     parent: 'entity',
            //     url: '/patient-medication',
            //     data: {
            //         authorities: ['ROLE_USER'],
            //         pageTitle: 'PatientMedications'
            //     },
            //     views: {
            //         'content@': {
            //             templateUrl: 'app/entities/patient-medication/patient-medications.html',
            //             controller: 'PatientMedicationController',
            //             controllerAs: 'vm'
            //         }
            //     },
            //     resolve: {}
            // })
            // .state('patient-medication-detail', {
            //     parent: 'entity',
            //     url: '/patient-medication/{id}',
            //     data: {
            //         authorities: ['ROLE_USER'],
            //         pageTitle: 'PatientMedication'
            //     },
            //     views: {
            //         'content@': {
            //             templateUrl: 'app/entities/patient-medication/patient-medication-detail.html',
            //             controller: 'PatientMedicationDetailController',
            //             controllerAs: 'vm'
            //         }
            //     },
            //     resolve: {
            //         entity: ['$stateParams', 'PatientMedication', function ($stateParams, PatientMedication) {
            //             return PatientMedication.get({id: $stateParams.id}).$promise;
            //         }],
            //         previousState: ["$state", function ($state) {
            //             var currentStateData = {
            //                 name: $state.current.name || 'patient-medication',
            //                 params: $state.params,
            //                 url: $state.href($state.current.name, $state.params)
            //             };
            //             return currentStateData;
            //         }]
            //     }
            // })
            // .state('patient-medication-detail.edit', {
            //     parent: 'patient-medication-detail',
            //     url: '/detail/edit',
            //     data: {
            //         authorities: ['ROLE_USER']
            //     },
            //     onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
            //         $uibModal.open({
            //             templateUrl: 'app/entities/patient-medication/patient-medication-dialog.html',
            //             controller: 'PatientMedicationDialogController',
            //             controllerAs: 'vm',
            //             backdrop: 'static',
            //             size: 'lg',
            //             resolve: {
            //                 entity: ['PatientMedication', function (PatientMedication) {
            //                     return PatientMedication.get({id: $stateParams.id}).$promise;
            //                 }]
            //             }
            //         }).result.then(function () {
            //             $state.go('^', {}, {reload: false});
            //         }, function () {
            //             $state.go('^');
            //         });
            //     }]
            // })


            // .state('patient-orders.newmed', {
            //     url: '/new-medication',
            //     ncyBreadcrumb: {
            //         label: 'New',
            //         parent: 'patient-orders'
            //     },
            //     data: {
            //         authorities: ['ROLE_USER'],
            //         pageTitle: 'Orders'
            //     },
            //     views: {
            //         'content@patient-abs': {
            //             templateUrl: 'app/entities/patient-medication/patient-medication-new.html',
            //             controller: 'PatientMedicationNewController',
            //             controllerAs: 'vm'
            //         }
            //     },
            //     resolve: {
            //         patientMedication: ['$stateParams', function ($stateParams) {
            //             return {
            //                 signatureDate: null,
            //                 signed: false,
            //                 id: null,
            //                 patientMedicationPress: []
            //             };
            //         }]
            //     }
            // })



            .state('patient-medication-update', {
                parent: 'patient-orders',
                url: '/{oid}/medupdate',
                ncyBreadcrumb: {
                    label: 'Medications Details',
                    parent: 'patient-orders'
                },
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Medications Details'
                },
                views: {
                    'content@patient-abs': {
                        templateUrl: 'app/entities/patient-medication/patient-medication-update.html',
                        controller: 'PatientMedicationUpdateController',
                        controllerAs: 'vm'
                    },
                    'medications-press@patient-medication-update': {
                        templateUrl: 'app/entities/patient-medication-pres/patient-medication-pres.html',
                        controller: 'PatientMedicationPresController',
                        controllerAs: 'vm',
                        resolve: {
                            canCancel: function () {
                                return true;
                            }
                        }
                    }
                },
                resolve: {
                    patientMedication: ['$stateParams', 'PatientMedication', function ($stateParams, PatientMedication) {
                        return PatientMedication.patientMedicationByTake({id: $stateParams.oid}).$promise;
                    }]
                }
            })
    }

})();
