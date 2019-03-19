(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('CoreService', CoreService);

    CoreService.$inject = ['$state', '$cookies', '$sessionStorage', 'Base64', 'moment', '$rootScope', 'Chart', 'DataUtils', '$q'];

    function CoreService ($state, $cookies ,$sessionStorage, Base64, moment, $rootScope, Chart, DataUtils, $q) {

        var service = {
            byId: byId,
            isActive: isActive,
            isDischarge: isDischarge,
            sortListName: sortListName,
            isWaiting: isWaiting,
            actualFacility: actualFacility,
            redirectSearch: redirectSearch,
            redirectPatientDetails: redirectPatientDetails,
            patientAge: patientAge,
            greaterValue: greaterValue,
            toggle: toggle,
            setCurrentEmployee: setCurrentEmployee,
            getCurrentEmployee: getCurrentEmployee,
            getAvatar: getAvatar,
            setCurrentFacility: setCurrentFacility,
            getCurrentFacility: getCurrentFacility,
            getDays: getDays,
            setUnassignedBeds: setUnassignedBeds,
            getUnassignedBeds: getUnassignedBeds,
            capitalizePatientName: capitalizePatientName,
            fullNameCapitalized: fullNameCapitalized,
            fullNameCapitalizedList:fullNameCapitalizedList,
            parseToTime: parseToTime,
            parseToDate: parseToDate,
            parseToDateTime: parseToDateTime,
            getSelectedChart: getSelectedChart,
            setSelectedChart: setSelectedChart,
            setCorporation: setCorporation,
            getCorporation: getCorporation,
            countDatesBetween: countDatesBetween,
            findNameByActualChartFromBed: findNameByActualChartFromBed,
            attachment: attachment,
            splitByBackSlash: splitByBackSlash,
            generateUUID:generateUUID,
            validateRemember:validateRemember,
            getUUID:getUUID,
            saveValidateCode:saveValidateCode,
            deleteValidateCode:deleteValidateCode,
            toLocalDate:toLocalDate

        };

        return service;

        function byId (id, list) {
            var i =0;
            var finded = false;
            var value;
            while (!finded && i < list.length) {
                if(list[i].id == id){
                    value = list[i];
                    finded = true;
                }
            }
            return value;
        };

        function isActive(data) {
            if(data){
                try {
                    var value = data.status.toUpperCase();
                    if(value == 'ACT')
                        return true;
                    else
                        return false;
                } catch (e) {
                    console.log(e);
                }

            }
            return false;
        };

        function greaterValue(a, b) {
            if(a && b){
                if(a >= b) {
                    return a
                } else if(a < b) {
                    return b;
                }
            }else
                return a;
        };

        function actualFacility(data) {
            if(data){
                if(parseInt(localStorage.getItem('facid')) == data)
                    return true;
                else
                    return false;

            }
            return false;
        };

        function toggle(item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) {
                list.splice(idx, 1);
            }
            else {
                list.push(item);
            }
        };

        function isDischarge(data) {
            var actualDate = moment(new Date()).format('YYYY-MM-DD');
            if(data > actualDate)
                return false
            else
                return true
        };

        function isWaiting(data) {
            if(data == 'yes')
                return true
            else
                return false
        };

        function patientAge(dateString) {
            var today = new Date();
            var birthDate = new Date(dateString);
            var age = today.getFullYear() - birthDate.getFullYear();
            var m = today.getMonth() - birthDate.getMonth();
            if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                age--;
            }
            return age;
        };

        function countDatesBetween(toDate, fromDate) {
            var magicNumber = (1000 * 60 * 60 * 24);
            if(toDate && fromDate){
                var dayDiff = Math.floor((toDate - fromDate) / magicNumber);
                if (angular.isNumber(dayDiff)){
                    return dayDiff + 1;
                }
            }
        };

        function redirectSearch() {
            $state.go('search')
        };

        function redirectPatientDetails(data) {
            if(data){
                localStorage.setItem('patId', data.chart.patientId);
                localStorage.setItem('chart', data.chart.id);
                localStorage.setItem('bed', data.chart.bedId);
            }
            $state.go('patient-details');
        };

        function sortListName(data) {
            data.sort(function (a, b) {
                var key1 = a.name;
                var key2 = b.name;

                if (key1 < key2) {
                    return -1;
                } else if (key1 == key2) {
                    return 0;
                } else {
                    return 1;
                }
            });
        };

        /**
         * Set a employee to the session storage
         * @param employee
         */
        function setCurrentEmployee(currentEmployee) {
            if (currentEmployee) {
                $sessionStorage.currentEmployee = Base64.encode(JSON.stringify(currentEmployee));
            }
        }

        /**
         * Get current employee logged from the session storage
         */
        function getCurrentEmployee() {
            if($sessionStorage.currentEmployee)
                return JSON.parse(Base64.decode($sessionStorage.currentEmployee));
            else
                return null
        }

        /**
         * Get random avatar by gender
         */
        function getAvatar(gender) {

            var maleAvatar = [
                'content/adminLTE/img/avatar.png',
                'content/adminLTE/img/avatar4.png',
                'content/adminLTE/img/avatar5.png'
            ];

            var femaleAvatar = [
                'content/adminLTE/img/avatar2.png',
                'content/adminLTE/img/avatar3.png'
            ];

            var ramdom = 0;
            if (gender == 'MALE') {
                ramdom = Math.floor((Math.random() * 3));
                return maleAvatar[ramdom];
            }
            else {
                ramdom = Math.floor((Math.random() * 2));
                return femaleAvatar[ramdom];
            }
        }

        /**
         * Set a facility to the session storage
         * @param facility
         */
        function setCurrentFacility(facility) {
            if (facility) {
                $sessionStorage.selectedFacility = Base64.encode(JSON.stringify(facility));
                // $rootScope.$broadcast('bluebookApp:setCurrentFacility', facility);
            }
        }

        /**
         * Get current facility from the session storage
         */
        function getCurrentFacility() {
            if($sessionStorage.selectedFacility)
                return JSON.parse(Base64.decode($sessionStorage.selectedFacility));
            else
                return null;
        }

        /**
         * Set a corporation to the session storage
         * @param corporation
         */
        function setCorporation(corporation) {
            if (corporation) {
                $sessionStorage.selectedCorporation = Base64.encode(JSON.stringify(corporation));
            }
        }

        /**
         * Get the corporation from the session storage
         */
        function getCorporation() {
            if($sessionStorage.selectedCorporation)
                return JSON.parse(Base64.decode($sessionStorage.selectedCorporation));
            else
                return null;
        }

        /**
         * Set a chart to the session storage
         * @param chart
         */
        function setSelectedChart(chart) {
            if (chart) {
                $sessionStorage.selectedChart = Base64.encode(JSON.stringify(chart));
            }
        }

        /**
         * Get selected chart from session storage
         */
        function getSelectedChart() {
            if($sessionStorage.selectedChart)
                return JSON.parse(Base64.decode($sessionStorage.selectedChart));
            else
                return null;
        }

        /**
         * Set a unassigned beds to the session storage
         * @param beds
         */
        function setUnassignedBeds(beds) {
            if (beds) {
                $sessionStorage.unassignedBeds = Base64.encode(JSON.stringify(beds));
            }
        }

        /**
         * Get current unassigned from the session storage
         */
        function getUnassignedBeds() {
            return JSON.parse(Base64.decode($sessionStorage.unassignedBeds));
        }

        function getDays() {
            return ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];
        }

        function capitalizePatientName(charts) {
            for(var i=0; i < charts.length; i++){
                charts[i].patient.fullNameCapital = charts[i].patient.firstName + ' ' + charts[i].patient.lastName.charAt(0).toUpperCase();
            }
        }

        function fullNameCapitalized(data) {
            return data.firstName + ' ' + data.lastName.charAt(0).toUpperCase();
        }

        function fullNameCapitalizedList(data) {
            for(var i=0; i < data.length; i++){
                data[i].fullNameCapital = data[i].firstName + ' ' + data[i].lastName.charAt(0).toUpperCase();
            }
        }

        function parseToTime(data) {
            return moment(data).format('hh:mm:ss a');
        }

        function parseToDate(data) {
            return moment(data).format('MM/DD/YYYY');
        }

        function parseToDateTime(data) {

            if(data) {
                return moment(toLocalDate(data)).format('MM/DD/YYYY hh:mm a');
            }else{
                return 'N/A';
            }
        }


        function toLocalDate(data){
            if(data){
                var dateUtc = moment.utc(data);
                var localDate = moment(dateUtc).local();
                return localDate;
            }else{
                return data;
            }
        }

        function findNameByActualChartFromBed(id){
            var value = {};
            Chart.get({id: id}, function (data) {
                value = data.patient.firstName + ' ' + data.patient.lastName.charAt(0).toUpperCase();
                return value;
            });
        }

        function attachment($file, data) {
            return $q(function (resolve, reject) {
                if ($file && $file.$error === 'pattern') {
                    reject($file.$error);
                }
                if ($file) {
                    DataUtils.toBase64($file, function(base64Data) {
                        data.file = base64Data;
                        data.fileContentType = $file.type;
                        data.name = $file.name;
                        data.size = bytesToSize($file.size);
                        resolve(data);
                    });
                }
            });
        }

        function bytesToSize(bytes) {
            var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
            if (bytes == 0) return 'n/a';
            var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
            if (i == 0) return bytes + ' ' + sizes[i];
            return (bytes / Math.pow(1024, i)).toFixed(3) + ' ' + sizes[i];
        };

        function splitByBackSlash(value) {
            return value.split('/');

        }

        function generateUUID() {
            var d = new Date().getTime();
            if(window.performance && typeof window.performance.now === "function"){
                d += performance.now();; //use high-precision timer if available
            }
            var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
                var r = (d + Math.random()*16)%16 | 0;
                d = Math.floor(d/16);
                return (c=='x' ? r : (r&0x3|0x8)).toString(16);
            });
            return uuid;
        };


        function validateRemember(){
            var employee = getCurrentEmployee();
            var remember =  $sessionStorage['browserUuid'+employee.id]? Base64.encode($sessionStorage['browserUuid'+employee.id]):null; //logged

            if(!remember){
                var location = window.location;
                var domain = location.protocol+'//'+location.hostname+(location.port ? ':'+location.port: '')+ '/#/login';
                window.location = domain;
            }
        }


        function getUUID(){

            var employee = getCurrentEmployee();
            var tmp = getCookie('browserUuid'+employee.id);
            var fromCookie =  tmp? Base64.decode(tmp): generateUUID();//If exits get, if no, generate
            setCookie('browserUuid'+employee.id, Base64.encode(fromCookie), 365);//1 year

            return fromCookie;
        }

        function saveValidateCode(){
            var employee = getCurrentEmployee();
            $sessionStorage['browserUuid' + employee.id] = Base64.encode('1111'); //logged
        }

        function deleteValidateCode(){
            var employee = getCurrentEmployee();
            $sessionStorage['browserUuid' + employee.id] = undefined; //
        }



        function setCookie(cname, cvalue, exdays) {
            var d = new Date();
            d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
            var expires = "expires="+d.toUTCString();
            document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
        }

        function getCookie(cname) {
            var name = cname + "=";
            var ca = document.cookie.split(';');
            for(var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') {
                    c = c.substring(1);
                }
                if (c.indexOf(name) == 0) {
                    return c.substring(name.length, c.length);
                }
            }
            return "";
        }




    }
})();
