(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('PatientMedication', PatientMedication);

    PatientMedication.$inject = ['$resource', 'DateUtils'];

    function PatientMedication ($resource, DateUtils) {
        var resourceUrl =  'api/patient-medications/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.signatureDate = DateUtils.convertLocalDateFromServer(data.signatureDate);
                        data.endDate = DateUtils.convertLocalDateFromServer(data.endDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.signatureDate = DateUtils.convertLocalDateToServer(copy.signatureDate);
                    copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.signatureDate = DateUtils.convertLocalDateToServer(copy.signatureDate);
                    copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
                    return angular.toJson(copy);
                }
            },
            'chart': {
                url: 'api/patient-medications/by-chart/:id', method: 'GET', isArray: true
            },
            'schedule': {
                url: 'api/patient-medications/schedule',  method: 'POST',
                // transformRequest: function (data) {
                //     // var copy = angular.copy(data);
                //     // if (copy.patientMedicationPress) {
                //     //     for (var i = 0; i < data.patientMedicationPress.length; i++) {
                //     //         copy.patientMedicationPress[i].staringDate =  DateUtils.convertLocalDateToServer(copy.patientMedicationPress[i].staringDate);
                //     //         if (copy.patientMedicationPress[i].endDate) {
                //     //             copy.patientMedicationPress[i].endDate =  DateUtils.convertLocalDateToServer(copy.patientMedicationPress[i].endDate);
                //     //         }
                //     //     }
                //     // }
                //
                //     // return angular.toJson(copy);
                // },
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.signatureDate = DateUtils.convertDateTimeFromServer(data.signatureDate);
                    data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);

                    for (var i = 0; i < data.patientMedicationPress.length; i++) {
                        data.patientMedicationPress[i].staringDate =  DateUtils.convertDateTimeFromServer(data.patientMedicationPress[i].staringDate);
                        if (data.patientMedicationPress[i].endDate) {
                            data.patientMedicationPress[i].endDate =  DateUtils.convertDateTimeFromServer(data.patientMedicationPress[i].endDate);
                        }

                        for (var j = 0; j < data.patientMedicationPress[i].patientMedicationTakes.length; j++) {
                            data.patientMedicationPress[i].patientMedicationTakes[j].scheduleDate =
                                DateUtils.convertDateTimeFromServer(data.patientMedicationPress[i].patientMedicationTakes[j].scheduleDate);
                        }
                    }

                    return data;
                }
            },
            'today_medications': {url: 'api/patient-medications/today/:id/:date', method: 'GET', isArray: true},
            'collect': {url: 'api/patient-medications/collect', method: 'POST'},
            'as_needed_medication' : {url : "api/patient-medications/today/as-needed/:id", method: 'GET', isArray: true},
            'add_needed_medication': {url: "api/patient-medications/add/as-needed", method: 'POST'},
            'unsigned' : {url : "api/patient-medications/unsigned/:id", method: 'GET', isArray: true},
            'all-unsigned' : {url : "api/patient-medications/unsigned/all/:id", method: 'GET', isArray: true},
            'signMedications': {url: 'api/patient-medications/sign-medications', method: 'POST'},
            'patientMedicationByTake': {url: 'api/patient-medication-by-take/:id', method: 'GET'},
	        'patientMedicationById': {url: 'api/patient-medication/generate-pres-pdf/:id', method: 'GET'},
            'getPatientMedicationByPress':{url: 'api/patient-medication-by-press/:id', method: 'GET'},
            'cancel':{url: 'api/patient-medication-cancel/:id', method: 'GET'},
            'sendFax':{url: 'api/patient-medication/send-efax/', method: 'POST'}
        });
    }
})();
