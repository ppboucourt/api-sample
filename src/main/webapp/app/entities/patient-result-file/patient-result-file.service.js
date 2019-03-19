(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('PatientResultFile', PatientResultFile);

    PatientResultFile.$inject = ['$resource', 'DataUtils'];

    function PatientResultFile ($resource, DataUtils) {
        var resourceUrl =  'api/patient-result-files/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'getByPatientResult': {
                url: 'api/patient-result-files/by-patient-result/getpdf',
                method: 'POST',
                responseType: 'arraybuffer',
                transformResponse: function (data, headers) {
                    DataUtils.downloadAndOpenPDFFile(data, headers);
                }
            },
            'update': { method:'PUT' },
            'getPdf': {
                url: 'api/patient-result-files/get-pdf',
                method: 'POST',
                responseType: 'arraybuffer',
                transformResponse: function (data, headers) {
                    DataUtils.downloadAndOpenPDFFile(data, headers);
                }
            }
        });
    }
})();
