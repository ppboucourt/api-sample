(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Forms', Forms);

    Forms.$inject = ['$resource'];

    function Forms ($resource) {
        var resourceUrl =  'api/forms/:id';

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
            'queryByFacility': {url: 'api/forms-by-facility/:id', method: 'GET', isArray: true },
            'parserForm': {url:'api/form/parse/:id', method: 'GET' },
            'patientProcess': {url:'api/forms/patient-process/:ppId/:facId', method: 'GET', isArray: true },
            'levelCare': {url: 'api/forms/all-forms-level-care/:ppId/:facId/:lcId', method: 'GET', isArray: true },
            'allFormsByChart': {url:'api/forms/all-forms-chart/:chId/:ppId', method: 'GET', isArray: true },
            'update': { method:'PUT' }
        });
    }
})();
