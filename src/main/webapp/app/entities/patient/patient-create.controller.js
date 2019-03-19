(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientCreateController', PatientCreateController);

    PatientCreateController.$inject = ['$state', 'PatientSearch',
        'GUIUtils', 'CoreService', '$sessionStorage', 'chart', 'TypeEthnicity', 'TypeMaritalStatus', 'TypeRace',
        'CountryState', 'TypePatientPrograms', 'ContactsFacility', 'Bed', 'TypePaymentMethods',
        'InsuranceType', 'InsuranceLevel', 'ContactsFacilitySearch',
        'InsuranceRelation', 'Chart', 'InsuranceCarrierSearch', 'TypeDischargeType', 'TypeLevelCare', 'DateUtils',
        'lodash', 'Icd10Search', 'DataUtils', 'fancyboxService'];

    function PatientCreateController($state, PatientSearch,
                                     GUIUtils, CoreService, $sessionStorage, chart, TypeEthnicity, TypeMaritalStatus, TypeRace,
                                     CountryState, TypePatientPrograms, ContactsFacility, Bed, TypePaymentMethods,
                                     InsuranceType, InsuranceLevel, ContactsFacilitySearch,
                                     InsuranceRelation, Chart, InsuranceCarrierSearch, TypeDischargeType, TypeLevelCare,
                                     DateUtils, _, Icd10Search, DataUtils, fancyboxService) {
        var vm = this;
        vm.facility = CoreService.getCurrentFacility();
        // loadMask.create('loadMaskUpdating', "Please wait, updating patient...", 'div.wrapper');
        //Functions
        vm.keepActiveTab = keepActiveTab;
        vm.getPatients = getPatients;
        vm.openCalendar = openCalendar;
        vm.addSecondaryInsurance = addSecondaryInsurance;
        vm.deleteSecondaryInsurance = deleteSecondaryInsurance;
        vm.deletePrimaryInsurance = deletePrimaryInsurance;
        vm.swapInsurances = swapInsurances;
        vm.save = save;
        vm.getContactsFacility = getContactsFacility;
        vm.checkChartData = checkChartData;
        vm.clearData = clearData;
        vm.openInsuranceCalendar = openInsuranceCalendar;
        vm.confirmDelete = confirmDelete;
        vm.cancelUpdate = cancelUpdate;
        vm.filterBed = filterBed;

        //Variable
        vm.form = {};
        vm.beds = Bed.freeBeds({id: vm.facility.id, actualChart: chart.id || 0});

        function filterBed() {
            return function (bed) {
                if (!vm.chart.patient.sex || bed.room.sex !== vm.chart.patient.sex) {
                    return false;
                } else {
                    return true;
                }
            };
        }

        vm.chart = chart;
        // console.log("vm.chart", vm.chart);
        vm.gender = GUIUtils.getGender();
        vm.maritalStatus = TypeMaritalStatus.query();
        vm.ethnicity = TypeEthnicity.query();
        vm.race = TypeRace.query();
        vm.states = CountryState.query();
        vm.typePatientPrograms = TypePatientPrograms.query();
        vm.paymentMethod = TypePaymentMethods.query();
        vm.insuranceType = InsuranceType.query();
        vm.insuranceLevels = InsuranceLevel.query();
        vm.insuranceRelations = InsuranceRelation.query();
        vm.typeDischargeType = TypeDischargeType.query();
        vm.typeLevelCare = TypeLevelCare.query();
        vm.icd10s = [];

        vm.activeTab = 0;

        // var queryStr = $location.search();
        // if (queryStr && queryStr.tab) { //100 -> address | 1 -> insurance
        //     vm.keepActiveTab(queryStr.tab.toString());
        // }

        vm.uiMaskOptions = {
            addDefaultPlaceholder: false
        };

        function keepActiveTab($index) {
            $sessionStorage.activePatientTab = {activeTab: $index};
        }


        function getPatients(firstName, lastName) {
            var must = [];

            if (firstName != null && firstName.length > 0) {
                must.push({match: {firstName: {query: firstName, fuzziness: 2}}});
            }

            if (lastName != null && lastName.length > 0) {
                must.push({match: {lastName: {query: lastName, fuzziness: 2}}});
            }

            if (must.length > 0) {
                must.push({term: {delStatus: false}});
                return PatientSearch.query({query: {bool: {must: must}}}).$promise;
            }
        }

        vm.selectPatient = function ($item, $model, $label) {
            vm.chart.patient = $model;
            vm.chart.patient.dateBirth = DateUtils.convertDateTimeFromServer(vm.chart.patient.dateBirth);
            loadLastChartByPatient(vm.chart.patient.id);
        };

        vm.oldChart = null;

        function loadLastChartByPatient(id) {
            Chart.lastChartByPatient({facId: CoreService.getCurrentFacility().id, patId: id}, function (result) {
                if (result.id) {
                    vm.oldChart = result;
                    clearChartData(vm.chart);
                    vm.chart.typeEthnicity = vm.oldChart.typeEthnicity;

                }
                // vm.chart.contacts = [];
                // vm.chart.allergies = [];
                // vm.chart.diets = [];
                // vm.chart.treatmentHistories = [];
                // vm.chart.hospitalizations = [];
                // vm.chart.chartToLevelCares = [];
                // vm.chart.drugs = [];
            })
        }

        function clearChartData(chart) {
            chart.phone = null;
            chart.altPhone = null;
            chart.email = null;
            chart.sobrietyDate = null;
            chart.address = null;
            chart.addressTwo = null;
            chart.city = null;
            chart.state = null;
            chart.zip = null;
            chart.typePatientPrograms = null;
            chart.referrer = null;
            chart.typePaymentMethods = null;
            chart.occupancy = null;
            chart.employer = null;
            chart.employerPhone = null;
            chart.admissionDate = null;
            chart.bed = null;
            chart.livingArrangement = null;
            chart.dischargeTo = null;
            chart.dischargeDate = null;
            chart.dateFirstContact = null;
            chart.firstContactName = null;
            chart.firstContactRelationship = null;
            chart.firstContactPhone = null;
            chart.typeDischargeType = null;
            chart.typeLevelCare = null;
            chart.referrerRequiredContact = false;
            chart.wayLiving = null;
            chart.insurances = [
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
                    wayLiving: null,
                    insuranceOrder: 1
                }
            ];
            // chart.contacts = [];
            // chart.allergies = [];
            // chart.diets = [];
            // chart.treatmentHistories = [];
            // chart.hospitalizations = [];
            // chart.chartToLevelCares = [];
            // chart.drugs = [];
            // chart.icd10S = [];
        }

        function clearPatientData(patient) {
            patient.firstName = null;
            patient.middleName = null;
            patient.lastName = null;
            patient.preferredName = null;
            patient.gender = null;
            patient.typeRace = null;
            patient.social = null;
            patient.dateBirth = null;
            patient.status = null;
            patient.sex = null;
        }

        function chartNewFields(chart) {
            chart.typeEthnicity = null;
            chart.typeMaritalStatus = null;
        }

        function clearData() {
            clearChartData(vm.chart);
            clearChartData(vm.chartBackUp);
            clearPatientData(vm.chart.patient);
            chartNewFields(vm.chart);
            vm.chartLoaded = !vm.chartLoaded;
            vm.patientLoaded = !vm.patientLoaded;
        }

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.dateBirth = false;
        vm.datePickerOpenStatus.sobrietyDate = false;
        vm.datePickerOpenStatus.admissionDate = false;
        vm.datePickerOpenStatus.dischargeDate = false;
        vm.datePickerOpenStatus.insuranceDob = false;
        vm.datePickerOpenStatus.dateFirstContact = false;
        vm.datePickerOpenStatus.insuranceDob = [false, false];

        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function openInsuranceCalendar(index) {
            vm.datePickerOpenStatus.insuranceDob[index] = true;
        }

        function addSecondaryInsurance() {
            vm.chart.insurances[1] = {};
            vm.chart.insurances[1].insuranceOrder = 2;
        }

        function deletePrimaryInsurance() {
            vm.swapInsurances();
            vm.deleteSecondaryInsurance();
        }

        function deleteSecondaryInsurance() {
            vm.chart.insurances.splice(1, 1);
        }

        function swapInsurances() {
            var tempInsurance = {};

            angular.copy(vm.chart.insurances[0], tempInsurance);
            angular.copy(vm.chart.insurances[1], vm.chart.insurances[0]);
            angular.copy(tempInsurance, vm.chart.insurances[1]);

            vm.chart.insurances[0].insuranceOrder = 1;
            vm.chart.insurances[1].insuranceOrder = 2;
        }

        function getContactsFacility(query) {
            return ContactsFacilitySearch.query({query: query}).$promise;
        }

        // Editor options.
        vm.ckOptions = {
            language: 'en',
            allowedContent: true,
            entities: false
        };

        vm.attachFile = function (file, insurance, type) {
            var fileEncode = {};
            if (file) {
                DataUtils.toBase64(file, function (base64Data) {
                    // console.log("base64Data", base64Data);
                    fileEncode.picture = base64Data;
                    fileEncode.pictureContentType = file.type;
                    if (type === 'FRONT') {
                        if (insurance.frontPicture && insurance.frontPicture.id) {
                            insurance.frontPicture = angular.extend({}, insurance.frontPicture, fileEncode);
                        } else {
                            insurance.frontPicture = fileEncode;
                        }
                    }
                    if (type === 'BACK') {
                        if (insurance.backPicture && insurance.backPicture.id) {
                            insurance.backPicture = angular.extend({}, insurance.backPicture, fileEncode);
                        } else {
                            insurance.backPicture = fileEncode;
                        }
                    }
                });
            }
            // console.log("insurance", insurance);
        }

        vm.getImage = function (picture) {
            console.log('data:' + picture.pictureContentType + ';base64,' + picture.picture);
            return 'data:' + picture.pictureContentType + ';base64,' + picture.picture;
        }

        vm.imageDetail = function (picture, name) {
            fancyboxService.fancyboxPlus()({
                'href': 'data:' + picture.pictureContentType + ';base64,' + picture.picture,
                'title': name,
                // 'titlePosition'  : 'over',
                'transitionIn': 'elastic',
                'transitionOut': 'elastic'
            });

        }

        vm.changePictureStatus = function (insurance, type) {
            console.log("insurance", insurance);
            if (type === 'BACK') {
                insurance.backPicture.delStatus = true;
            }
            if (type === 'FRONT') {
                insurance.frontPicture.delStatus = true;
            }
        }

        function save() {
            vm.isSaving = true;
            // loadMask.show('#loadMaskUpdating');
            if (vm.chart.dischargeTo2) {
                vm.chart.dischargeTo = vm.chart.dischargeTo2.name;
            }

            if (vm.chart.referrer2) {
                vm.chart.referrer = vm.chart.referrer2.name;
            }

            vm.chart.reason = vm.chart.wayLiving;
            vm.chart.dischargeTime = vm.chart.dischargeDate;

            vm.chart.chartToLevelCares = [];
            vm.chart.chartToLevelCares.push(chartToLevelCare(vm.chart.typeLevelCare));
            // vm.chart.picture = GUIUtils.getDefaultUserPicture();
            // vm.chart.pictureContentType = GUIUtils.getDefaultUserPictureContentType();
            // vm.chart.employees = CoreService.getCurrentEmployee();
            // vm.chart.status = 'ACT';
            // vm.chart.patient.status = 'ACT';

            //If we have insurance pictures make transformation with the images
            console.log("vm.chart", vm.chart);
            if (chart.id !== null) {
                // vm.ChartToForm.charToFormByChart({id: chart.id }, function (data){
                //     vm.chart.chartToForms = data;
                Chart.update(vm.chart, onSaveSuccess, onSaveError);
                // }) ;
            } else {
                Chart.save(vm.chart, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            // loadMask.hide('#loadMaskUpdating');
            $state.go('patient', {id: result.id}, {reload: true});
        }

        function onSaveError(error) {
            // loadMask.hide('#loadMaskUpdating');
            vm.isSaving = false;
        }

        function cancelUpdate() {
            $state.go('patient', {}, {reload: true});
        }

        function chartToLevelCare(typeLevelCare) {
            var chartToLevelCare = {};
            chartToLevelCare.typeLevelCare = typeLevelCare;
            return chartToLevelCare;
        }

        function checkChartData(field) {
            vm.chart[field] = vm.chartBackUp[field];
            $('#' + field).prop("disabled", true);
        }

        vm.changePaymentMethod = function () {
            if (vm.chart.typePaymentMethods.category == 'Insurance') {
                vm.chart.insurances = [
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
                        wayLiving: null,
                        insuranceOrder: 1
                    }
                ];
            } else {
                vm.chart.insurances = [];
            }
        };

        vm.getInsuranceCarrier = function (query) {
            if (query && query.length > 2) {
                InsuranceCarrierSearch.query({query: query}, function (result) {
                    vm.carriers = result;
                });
            }
        };

        // vm.getDrugs = function(query) {
        //     if (query && query.length > 2) {
        //         DrugsSearch.query({query: query}, function (result) {
        //             vm.drugs = result;
        //         });
        //     }
        // }

        vm.getIcd10s = function (query) {
            if (query && query.length > 2) {
                Icd10Search.query({query: query}, function (result) {
                    vm.icd10s = result;
                });
            }
        };

        vm.clear = function () {
            $state.go("patient-create", null, {reload: true});
        };

        function confirmDelete(id) {
            Chart.delete({id: id},
                function () {
                    $state.go('current-patients', null, {reload: 'current-patients'});
                });
        }


    }
})();
