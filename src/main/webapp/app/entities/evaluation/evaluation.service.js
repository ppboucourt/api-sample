(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Evaluation', Evaluation);

    Evaluation.$inject = ['$resource', 'DateUtils'];

    function Evaluation ($resource, DateUtils) {
        var resourceUrl =  'api/evaluations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.daily_start_time = DateUtils.convertDateTimeFromServer(data.daily_start_time);
                        // data.jsonTemplate = angular.fromJson(data.jsonTemplate);
                        // data.jsonData = angular.fromJson(data.jsonData);
                    }
                    return data;
                }
            },
            'byChartPatientProcess': { url: 'api/evaluations/patient-forms/:chId/:ppId', method: 'GET', isArray: true },
            'assignEvaluation': { url: 'api/evaluations/assign', method: 'POST' },
            'update': { method:'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    // copy.jsonTemplate = angular.toJson(copy.jsonTemplate);
                    // copy.jsonData = angular.toJson(copy.jsonData);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
