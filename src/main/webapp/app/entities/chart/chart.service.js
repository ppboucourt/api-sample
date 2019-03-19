(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Chart', Chart);

    Chart.$inject = ['$resource', 'DateUtils', 'GUIUtils'];

    function Chart ($resource, DateUtils, GUIUtils) {
        var resourceUrl =  'api/charts/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.admissionDate = DateUtils.convertDateTimeFromServer(data.admissionDate);
                        data.dischargeDate = DateUtils.convertDateTimeFromServer(data.dischargeDate);
                        data.dischargeTime = DateUtils.convertDateTimeFromServer(data.dischargeTime);
                        data.dateFirstContact = DateUtils.convertDateTimeFromServer(data.dateFirstContact);
                        data.sobrietyDate = DateUtils.convertDateTimeFromServer(data.sobrietyDate);
                        if(data.patient)
                            data.patient.dateBirth = DateUtils.convertDateTimeFromServer(data.patient.dateBirth);
                        if(!data.picture)
                            data.picture = GUIUtils.getDefaultUserPicture();
                        if(!data.pictureContentType)
                            data.pictureContentType = GUIUtils.getDefaultUserPictureContentType();
                        data.phone = parseInt(data.phone);
                        data.altPhone = parseInt(data.altPhone);

                        if (data.insurances) {
                            for (var i = 0; i < data.insurances.length; i++) {
                                data.insurances[i].dob = DateUtils.convertLocalDateFromServer(data.insurances[i].dob);
                            }
                        }
                        if(data.patient) {
                            data.patient.dateBirth = DateUtils.convertDateTimeFromServer(data.patient.dateBirth);
                        }
                    }
                    return data;
                }
            },
            'byFacility': {url: 'api/charts-facility/:id', method: 'GET', isArray: true},
            'unassignedBed': {url: 'api/charts-facility/unassigned-bed/:id', method: 'GET', isArray: true},
            'occupancyChart': {url: 'api/charts/chart-occupancy', method: 'PUT'},
            'currentPatient': {url: 'api/charts-current-patients/:id', method: 'GET', isArray: true},
            'waitingRoom': {url: 'api/charts-waiting-room/:id', method: 'GET', isArray: true},
            'archive': {url: 'api/charts-archive/:id', method: 'GET', isArray: true},
            'allCharts': {url: 'api/all-charts/:id?page=:page&size=:size&state=:state&query=:query', method: 'GET', isArray: true},
            'medicationsDate': {url: 'api/charts-medications/:id', method: 'GET', isArray: true},
            'lastChartByPatient': {url: 'api/last-chart-by-patient/:facId/:patId', method: 'GET', transformResponse: function (data) {
                if(data){
                    data = angular.fromJson(data);
                    data.admissionDate = DateUtils.convertDateTimeFromServer(data.admissionDate);
                    data.dischargeDate = DateUtils.convertDateTimeFromServer(data.dischargeDate);
                    data.dischargeTime = DateUtils.convertDateTimeFromServer(data.dischargeTime);
                    data.dateFirstContact = DateUtils.convertDateTimeFromServer(data.dateFirstContact);
                    data.sobrietyDate = DateUtils.convertDateTimeFromServer(data.sobrietyDate);
                    if (data.patient)
                        data.patient.dateBirth = DateUtils.convertDateTimeFromServer(data.patient.dateBirth);
                }
                return data;
            }},
            'updateWaiting': {url: 'api/update-waiting/:id', method: 'GET'},
            'updateWaitingWithForms': {url: 'api/chart/preload-forms', method: 'PUT'},
            'update': { method:'PUT' },
            'currentPatientVO': {url: 'api/charts-current-patients-vo/:id', method: 'GET', isArray: true},
            'patientByChart': {url: "api/charts/patient/:id", method: 'GET'},
            'updatePictureVO': { 'url': 'api/update-chart-picture', method: 'PUT', isArray: false},
            'findByChartToForm': { 'url': 'api/chart-find-by-chart-to-form/:id', method: 'GET', isArray: false},
            'chartsCurrentPatientsVOForGroupSession': { 'url': 'api/charts-current-patients-vo-for-group-session/:id', method: 'GET', isArray: true}
        });
    }
})();
