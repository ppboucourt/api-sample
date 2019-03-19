(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('ChartToMedications', ChartToMedications);

    ChartToMedications.$inject = ['$resource', 'DateUtils'];

    function ChartToMedications ($resource, DateUtils) {
        var resourceUrl =  'api/chart-to-medications/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.datePrescription = DateUtils.convertDateTimeFromServer(data.datePrescription);
                    }
                    return data;
                }
            },
            'medicationsChartByFacility': {url: 'api/charts-medications-facility/:id', method: 'GET', isArray: true},
            'prescriptionDistinct': {url: 'api/prescription-distinct/:id', method: 'GET', isArray: true},
            'update': { method:'PUT' }
        });
    }
})();
