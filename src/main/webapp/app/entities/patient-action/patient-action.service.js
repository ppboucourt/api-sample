(function () {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('PatientAction', PatientAction);

    PatientAction.$inject = ['$resource', 'DateUtils'];

    function PatientAction($resource, DateUtils) {
        var resourceUrl = 'api/patient-actions/:id';

        return $resource(resourceUrl, {}, {
            'query': {method: 'GET', isArray: true},
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
                url: 'api/patient-actions/by-chart/:id', method: 'GET', isArray: true
            },
            'schedule': {
                url: 'api/patient-actions/schedule', method: 'POST',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.signatureDate = DateUtils.convertDateTimeFromServer(data.signatureDate);
                    data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);

                    for (var i = 0; i < data.patientActionPres.length; i++) {
                        data.patientActionPres[i].staringDate = DateUtils.convertDateTimeFromServer(data.patientActionPres[i].staringDate);
                        if (data.patientActionPres[i].endDate) {
                            data.patientActionPres[i].endDate = DateUtils.convertDateTimeFromServer(data.patientActionPres[i].endDate);
                        }

                        for (var j = 0; j < data.patientActionPres[i].patientActionTakes.length; j++) {
                            data.patientActionPres[i].patientActionTakes[j].scheduleDate =
                                DateUtils.convertDateTimeFromServerM(data.patientActionPres[i].patientActionTakes[j].scheduleDate);

                            data.patientActionPres[i].patientActionTakes[j].collectedDate =
                                DateUtils.convertDateTimeFromServerM(data.patientActionPres[i].patientActionTakes[j].collectedDate);
                        }
                    }

                    return data;
                }
            },
            'today_actions': {url: 'api/patient-actions/today/:id/:date', method: 'GET', isArray: true},
            'today_actions_vo': {
                url: 'api/patient-actions-vo/today/:id/:date', method: 'GET', isArray: true,
                // transformResponse: function (data) {
                //     data = angular.fromJson(data);
                //
                //     for (var i = 0; i < data.length; i++) {
                //         data[i].collectedDate =  DateUtils.convertDateTimeFromServerM(data[i].collectedDate);
                //     }
                //
                //     return data;
                // }
            },
            'collect': {url: 'api/patient-actions/collect', method: 'POST'},
            'as_needed_actions': {url: "api/patient-actions/today/as-needed/:id", method: 'GET', isArray: true},
            'add_needed_action': {url: "api/patient-actions/add/as-needed", method: 'POST'},
            'unsigned': {url: "api/patient-actions/unsigned/:id", method: 'GET', isArray: true},
            'all-unsigned': {url: "api/patient-actions/unsigned/all/:id", method: 'GET', isArray: true},
            'signActions': {url: 'api/patient-actions/sign-orders', method: 'POST'},
            'getAllByChartVo': {url: "api/patient-actions/get-all-by-chart-vo/:id", method: 'GET', isArray: true},
            'cancel': {url: 'api/patient-actions/patient-medication-cancel/:id', method: 'GET'},

        });
    }
})();
