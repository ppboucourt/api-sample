(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('ChartToForm', ChartToForm);

    ChartToForm.$inject = ['$resource'];

    function ChartToForm ($resource) {
        var resourceUrl =  'api/chart-to-forms/:id';

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
            'byChartPatientProcess': { url: 'api/chart-to-forms/chart-patient-process/:chId/:ppId', method: 'GET', isArray: true },
            'assignForms': { url: 'api/chart-to-forms/assign', method: 'POST' },
            'saveSign': { url: 'api/chart-to-forms/save-sign', method: 'PUT' },
            'getVO': {url: 'api/chart-to-forms/vo/:id', method: 'GET'},
            'update': { method:'PUT' },
            'charToFormByChart': {url: 'api/chart-to-forms/chart/:id', method: 'GET', isArray: true }
        });
    }
})();
