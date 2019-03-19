(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('EvaluationTemplate', EvaluationTemplate);

    EvaluationTemplate.$inject = ['$resource'];

    function EvaluationTemplate ($resource) {
        var resourceUrl =  'api/evaluation-templates/:id';

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
            'byFacility': { url: 'api/evaluation-template/facility/:id', method: 'GET', isArray: true },
            'patientProcess': { url: 'api/evaluation-template/patient-process/:ppId/:facId', method: 'GET', isArray: true },
            'levelCare': {url: 'api/evaluation-template/level-care/:ppId/:facId/:lcId', method: 'GET', isArray: true },
            'update': { method:'PUT' }
        });
    }
})();
