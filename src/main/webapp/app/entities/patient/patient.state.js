(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
            .state('patient-abs', {
                parent: 'entity',
                url: '/patient/{id}',
                abstract: true,
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_PRIMARY_THERAPIST,
                        ROLES.ROLE_OTHER_THERAPIST,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_BHT,
                        ROLES.ROLE_LAB
                    ],
                    pageTitle: 'Patient'
                },
                ncyBreadcrumb: {
                    label: 'Patient Details'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/patient/patient-abs.html',
                        controller: 'PatientController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    chart: ['$stateParams', 'Chart', 'CoreService', function ($stateParams, Chart, CoreService) {
                        return Chart.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'patient',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('patient', {
                parent: 'patient-abs',
                url: '/details',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_PRIMARY_THERAPIST,
                        ROLES.ROLE_OTHER_THERAPIST,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_BHT,
                        ROLES.ROLE_LAB
                    ],
                    pageTitle: 'Patient'
                },
                ncyBreadcrumb: {
                    label: 'Patient Details'
                },
                views: {
                    'content@patient-abs': {
                        templateUrl: 'app/entities/patient/patient-detail.html',
                        controller: 'PatientDetailController',
                        controllerAs: 'vm'
                    },
                    'contact@patient': {
                        templateUrl: 'app/entities/contacts/contacts.html',
                        controller: 'ContactsController',
                        controllerAs: 'vm'
                    },
                    'chartcareteam@patient': {
                        templateUrl: 'app/entities/chart-care-team/chart-care-teams.html',
                        controller: 'ChartCareTeamController',
                        controllerAs: 'vm'
                    },
                    'allergies@patient': {
                        templateUrl: 'app/entities/allergies/allergies.html',
                        controller: 'AllergiesController',
                        controllerAs: 'vm'
                    },
                    'diets@patient': {
                        templateUrl: 'app/entities/diet/diets.html',
                        controller: 'DietController',
                        controllerAs: 'vm'
                    },
                    'treatment-history@patient': {
                        templateUrl: 'app/entities/treatment-history/treatment-histories.html',
                        controller: 'TreatmentHistoryController',
                        controllerAs: 'vm'
                    },
                    'hospitalization@patient': {
                        templateUrl: 'app/entities/hospitalization/hospitalizations.html',
                        controller: 'HospitalizationController',
                        controllerAs: 'vm'
                    },
                    'urgent-issues@patient': {
                        templateUrl: 'app/entities/urgent-issues/urgent-issues.html',
                        controller: 'UrgentIssuesController',
                        controllerAs: 'vm'
                    },
                    'concurrent-reviews@patient': {
                        templateUrl: 'app/entities/concurrent-reviews/concurrent-reviews.html',
                        controller: 'ConcurrentReviewsController',
                        controllerAs: 'vm'
                    },
                    'patient-forms@patient': {
                        templateUrl: 'app/entities/forms/forms-patient.html',
                        controller: 'FormsPatientController',
                        controllerAs: 'vm'
                    },
                    'patient-orders@patient': {
                        templateUrl: 'app/entities/forms/forms-patient.html',
                        controller: 'FormsPatientController',
                        controllerAs: 'vm'
                    },
                    //Inline add order
                    'add-order@patient': {
                        templateUrl: 'app/entities/patient-order/patient-order-new.html',
                        controller: 'PatientOrderNewController',
                        controllerAs: 'vm'
                    },
                    //Inline edit order
                    'edit-order@patient': {
                        templateUrl: 'app/entities/patient-order/patient-order-update.html',
                        controller: 'PatientOrderUpdateController',
                        controllerAs: 'vm'
                    },
                    'edit-order-tests@patient': {
                        templateUrl: 'app/entities/patient-order-test/patient-order-tests.html',
                        controller: 'PatientOrderTestController',
                        controllerAs: 'vm',
                        resolve: {
                            canCancel: function () {
                                return true;
                            }
                        }
                    },
                    // 'mars@patient': {
                    //     templateUrl: 'app/entities/vitals/vitals.html',
                    //     controller: 'VitalsController',
                    //     controllerAs: 'vm'
                    // },
                    'patient-mars@patient': {
                        templateUrl: 'app/entities/mars/mars-patient.html',
                        controller: 'MarsPatientController',
                        controllerAs: 'vm'
                    },
                    'group-session-details-chart@patient': {
                        templateUrl: 'app/entities/group-session-details-chart/group-session-details-charts.html',
                        controller: 'GroupSessionDetailsChartController',
                        controllerAs: 'vm'
                    },
                    //Lab Results
                    'lab-results@patient': {
                        templateUrl: 'app/entities/patient-result/patient-result-by-patient.html',
                        controller: 'PatientResultByPatientController',
                        controllerAs: 'vm'
                    },

                    //Vitals mars
                    'vitals@patient': {
                        templateUrl: 'app/entities/vitals/vitals.html',
                        controller: 'VitalsController',
                        controllerAs: 'vm'
                    },
                    'glucoses@patient': {
                        templateUrl: 'app/entities/glucose/glucoses.html',
                        controller: 'GlucoseController',
                        controllerAs: 'vm'
                    },
                    'weight@patient': {
                        templateUrl: 'app/entities/weight/weights.html',
                        controller: 'WeightController',
                        controllerAs: 'vm'
                    },
                    'actions@patient': {
                        templateUrl: 'app/entities/patient-schedule/patient-actions-schedule.html',
                        controller: 'ActionsScheduleController',
                        controllerAs: 'vm'
                    },
                    'medications@patient': {
                        templateUrl: 'app/entities/patient-schedule/patient-medications-schedule.html',
                        controller: 'MedicationsScheduleController',
                        controllerAs: 'vm'
                    },
                    'result@patient': {
                        templateUrl: 'app/entities/patient-result/patient-result-by-patient.html',
                        controller: 'PatientResultByPatientController',
                        controllerAs: 'vm'
                    },
                    //Orders
                    'orders@patient': {
                        templateUrl: 'app/entities/patient-order/current-order/patient-order-current.html',
                        controller: 'PatientOrderCurrentController',
                        controllerAs: 'vm'
                    },
                    'lab@patient': {
                        templateUrl: 'app/entities/patient-order/current-order/patient-order-onetime.html',
                        controller: 'OneTimeCurrentOrderController',
                        controllerAs: 'vm'
                    },
                    'medication@patient': {
                        templateUrl: 'app/entities/patient-medication/patient-medications.html',
                        controller: 'PatientMedicationController',
                        controllerAs: 'vm'
                    },
                    'action@patient': {
                        templateUrl: 'app/entities/patient-action/patient-actions.html',
                        controller: 'PatientActionController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    listPatientProcess: ['TypePatientProcess', 'CoreService', function (TypePatientProcess, CoreService) {
                        return TypePatientProcess.byTypePage({
                            pagId: 1,
                            facId: CoreService.getCurrentFacility().id
                        }).$promise;
                    }],
                    hasPrinter: ['DYMO', function (DYMO) {
                        return DYMO.loadPrinters();
                    }],
                    filterRes: function () {
                        return {
                            start: null,
                            end: null,
                            abnormal: null,
                            status: null
                        }
                    },
                    patientOrder: ['$stateParams', function ($stateParams) {
                        return {
                            signatureDate: null,
                            signed: null,
                            endDate: null,
                            id: null,
                            patientOrderTests: []
                        };
                    }]
                }
            })
            .state('patient-create', {
                parent: 'entity',
                url: '/patient-create',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_PRIMARY_THERAPIST,
                        ROLES.ROLE_OTHER_THERAPIST,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_BHT,
                        ROLES.ROLE_LAB
                    ],
                    pageTitle: 'Patients'
                },
                ncyBreadcrumb: {
                    label: 'Create Patient'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/patient/patient-create.html',
                        controller: 'PatientCreateController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    chart: ['CoreService', 'GUIUtils', function (CoreService, GUIUtils) {
                        return {
                            oneTimeOnly: false,
                            sobrietyDate: null,
                            phone: null,
                            altPhone: null,
                            email: null,
                            address: null,
                            addressTwo: null,
                            city: null,
                            zip: null,
                            livingArrangement: null,
                            referrerRequired: null,
                            admissionDate: null,
                            dischargeDate: null,

                            employees: CoreService.getCurrentEmployee(),
                            repName: CoreService.getCurrentEmployee().firstName + ' ' + CoreService.getCurrentEmployee().lastName,
                            facility: CoreService.getCurrentFacility(),
                            picture: GUIUtils.getDefaultUserPicture(),
                            pictureContentType: GUIUtils.getDefaultUserPictureContentType(),
                            pictureRef: {
                                picture: GUIUtils.getDefaultUserPicture(),
                                pictureContentType: GUIUtils.getDefaultUserPictureContentType()
                            },
                            waitingRoom: true,
                            status: 'ACT',

                            occupancy: null,
                            employer: null,
                            employerPhone: null,
                            referrerRequiredContact: false,
                            dateFirstContact: null,
                            firstContactName: null,
                            firstContactRelationship: null,
                            firstContactPhone: null,
                            wayLiving: null,
                            patient: {
                                firstName: null,
                                middleName: null,
                                lastName: null,
                                preferredName: null,
                                gender: null,
                                race: null,
                                social: null,
                                dateBirth: null,
                                status: 'ACT'
                            },
                            insurances: [
                                {
                                    address: null,
                                    address2: null,
                                    city: null,
                                    dob: null,
                                    employer: null,
                                    firstName: null,
                                    gender: null,
                                    groupName: null,
                                    groupNumber: null,
                                    lastName: null,
                                    middleInitial: null,
                                    phone: null,
                                    planNumber: null,
                                    policyNumber: null,
                                    zipCode: null,
                                    insuranceOrder: 1
                                }
                            ],
                            typeEthnicity: null
                        };
                    }]
                }
            })
            .state('patient-edit', {
                parent: 'patient-abs',
                url: '/edit',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_PRIMARY_THERAPIST,
                        ROLES.ROLE_OTHER_THERAPIST,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_BHT,
                        ROLES.ROLE_LAB
                    ],
                    pageTitle: 'Patients'
                },
                ncyBreadcrumb: {
                    label: 'Update Patient'
                },
                views: {
                    'content@patient-abs': {
                        templateUrl: 'app/entities/patient/tpl/patient-form-tpl.html',
                        controller: 'PatientCreateController',
                        controllerAs: 'vm'
                    }
                }
            })
            .state('patient-orders', {
                parent: 'patient-abs',
                url: '/orders',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_PRIMARY_THERAPIST,
                        ROLES.ROLE_OTHER_THERAPIST,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_BHT,
                        ROLES.ROLE_LAB
                    ],
                    pageTitle: 'Patient'
                },
                ncyBreadcrumb: {
                    label: 'Patient Orders'
                },
                views: {
                    'content@patient-abs': {
                        templateUrl: 'app/entities/patient-order/current-order/patient-order-current.html',
                        controller: 'PatientOrderCurrentController',
                        controllerAs: 'vm'
                    },
                    'lab@patient-orders': {
                        templateUrl: 'app/entities/patient-order/current-order/patient-order-onetime.html',
                        controller: 'OneTimeCurrentOrderController',
                        controllerAs: 'vm'
                    },
                    'medication@patient-orders': {
                        templateUrl: 'app/entities/patient-medication/patient-medications.html',
                        controller: 'PatientMedicationController',
                        controllerAs: 'vm'
                    },
                    'action@patient-orders': {
                        templateUrl: 'app/entities/patient-action/patient-actions.html',
                        controller: 'PatientActionController',
                        controllerAs: 'vm'
                    }
                }
            })
            .state('patient-details-n', {
                parent: 'entity',
                url: '/patient-details-n',
                data: {
                    authorities: [
                        // ROLES.ROLE_SUPER_ADMIN,
                        // ROLES.ROLE_PROGRAM_DIRECTOR,
                        // ROLES.ROLE_CASE_MANAGER,
                        // ROLES.ROLE_DIRECTOR_NURSE,
                        // ROLES.ROLE_REGISTER_NURSE,
                        // ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        // ROLES.ROLE_PRIMARY_THERAPIST,
                        // ROLES.ROLE_OTHER_THERAPIST,
                        // ROLES.ROLE_CLINICAL_DIRECTOR,
                        // ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        // ROLES.ROLE_MD,
                        // ROLES.ROLE_BHT,
                        // ROLES.ROLE_LAB
                    ],
                    pageTitle: 'Patient Detail New'
                },
                ncyBreadcrumb: {
                    parent: 'entity',
                    label: 'Patient Details New'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/patient/patient-details/patient-details.html',
                        controller: 'PatientDetailControllerN',
                        controllerAs: 'vm'
                    }
                },
                resolve: {

                }

            })
    }

})();
