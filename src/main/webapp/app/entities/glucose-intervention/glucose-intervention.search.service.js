(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('GlucoseInterventionSearch', GlucoseInterventionSearch);

    GlucoseInterventionSearch.$inject = ['$resource'];

    function GlucoseInterventionSearch($resource) {
        var resourceUrl =  'api/_search/glucose-interventions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
